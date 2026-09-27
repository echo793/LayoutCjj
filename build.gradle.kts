// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    // Kotlin 2.0+ Compose 编译器插件，版本与项目 Kotlin 版本一致
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}