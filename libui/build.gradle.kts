import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")//或者 id("kotlin-android")
    kotlin("android.extensions")
    kotlin("kapt")
    //2、发布到jitpack.io的步骤2,似乎所有依赖module都要配置
    id("com.github.dcendents.android-maven")
}

android {
    compileSdkVersion(compileSdk)
    buildToolsVersion(buildTools)

    defaultConfig {
        minSdkVersion(minSdkNum)
        targetSdkVersion(targetSdkNum)
        versionCode = libCode
        versionName = libVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

//AndroidStudio 4 以下使用这种方式配置dataBinding
//    dataBinding {
//        isEnabled = true
//    }
    //AS4 以上版本使用这个配置dataBinding
    buildFeatures.dataBinding = true

}

dependencies {
    //这里演示了不同的kts下的添加依赖的方式
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
//    implementation(group = "org.jetbrains.kotlinx",name = "kotlinx-coroutines-android",version = "1.3.3")
    implementation(DepLibrary.APPCOMPAT)
    implementation(DepLibrary.CORE_KTX)
    implementation(DepLibrary.COROUTINES_ANDROID)
    implementation(DepLibrary.MATERIAL)
    implementation(DepLibrary.CONSTRAINT_LAYOUT)
    implementation(DepLibrary.RECYCLER_VIEW)

    //依赖core
    api(project(":libcore"))

    //test dependencies，使用的是buildSrc下面的自定义扩展
    addTestDependencies()

}