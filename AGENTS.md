# AGENTS.md

## 项目概述

单模块 Android Kotlin 应用「星星闹钟助手」。被「星星充电」App 通过 MIUI 链式启动拉起，在 0:00–7:59 时间窗口内设置 7:58 闹钟。

包名：`com.starlightalarm` • 唯一 Activity：`MainActivity`

## 构建

```bash
./gradlew assembleDebug
```

Release：`./gradlew assembleRelease`。未配置签名，Debug 默认签名。

AGP 8.2.0 / Kotlin 1.9.20 / compileSdk 34 / minSdk 21

## 测试与 Lint

无测试、无 lint 配置。如需添加，单元测试用 `./gradlew test`，仪器测试用 `./gradlew connectedAndroidTest`。

## 架构

- `app/src/main/java/com/starlightalarm/MainActivity.kt` — 唯一逻辑文件
- `app/src/main/AndroidManifest.xml` — 声明 `SET_ALARM` 权限，单 exported Activity
- `app/src/main/res/layout/activity_main.xml` — UI 布局

无数据层、网络、DI 或后台服务。

## 注意事项

- `EXTRA_SKIP_UI = false` 是刻意的 — 用户必须在系统闹钟界面点「确定」。
- 时间窗口判断（`hour in 0..7`）是核心逻辑，修改前务必同步更新 README。
- 仓库未提交 `gradlew`/`gradlew.bat` — 确认 `gradle/wrapper/` 存在，或通过 Android Studio 重新生成。
- 仅 MIUI 12.5+ 支持链式启动，非 MIUI 设备无法自动触发本 App。
