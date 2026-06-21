package com.starlightalarm

import android.app.AlarmClock
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshTime()
        trySetAlarm()

        findViewById<Button>(R.id.btnRetry).setOnClickListener {
            trySetAlarm()
        }

        findViewById<Button>(R.id.btnClose).setOnClickListener {
            finish()
        }
    }

    private fun refreshTime() {
        val now = System.currentTimeMillis()
        val timeStr = DateFormat.format("HH:mm:ss", now).toString()
        val hour = DateFormat.format("HH", now).toString().toInt()
        val inWindow = hour in 0..7

        findViewById<TextView>(R.id.tvTime).text = timeStr

        findViewById<TextView>(R.id.tvHint).text = if (inWindow) {
            "当前时间 8:00 前，正在触发闹钟设置..."
        } else {
            "当前时间 8:00 后，本次启动不设闹钟（8:00 前启动才会触发）"
        }
    }

    private fun trySetAlarm() {
        val now = System.currentTimeMillis()
        val hour = DateFormat.format("HH", now).toString().toInt()
        val inWindow = hour in 0..7

        findViewById<TextView>(R.id.tvResult).text = if (!inWindow) {
            "未触发：时间不在 8:00 前的窗口内（仅作为被链式启动的触发器使用）"
        } else {
            val ok = launchSetAlarmUi(7, 58)
            if (ok) "已跳转系统闹钟设置 → 请点『确定』完成 7:58 闹钟"
            else "跳转失败：未找到可用的闹钟App"
        }
    }

    private fun launchSetAlarmUi(hour: Int, minute: Int): Boolean {
        return try {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, hour)
                putExtra(AlarmClock.EXTRA_MINUTES, minute)
                putExtra(AlarmClock.EXTRA_SKIP_UI, false)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
