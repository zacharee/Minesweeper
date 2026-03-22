plugins {
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidApplication)
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.arkivanov.minesweeper"

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
}

dependencies {
    implementation(project(":composeApp"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.compose)
    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.timetravel)
    implementation(libs.mvikotlin.main)
    implementation(libs.essenty.lifecycle.coroutines)
    implementation(libs.decompose)
}
