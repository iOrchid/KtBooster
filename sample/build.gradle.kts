plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(compileSdk)
    buildToolsVersion(buildTools)

    defaultConfig {
        applicationId = "org.zhiwei.booster"
        minSdkVersion(minSdk)
        targetSdkVersion(targetSdk)
        versionCode = libCode
        versionName = libVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

//AndroidStudio 4 以下使用这种方式配置dataBinding
//	dataBinding {
//		isEnabled = true
//	}
    //AS4 以上版本使用这个配置dataBinding
    buildFeatures.dataBinding = true

//在Project的build.gradle.kts中统一配置了
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }

}

dependencies {
    //这里演示了不同的kts下的添加依赖的方式
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION))
    //    implementation(group = "org.jetbrains.kotlinx",name = "kotlinx-coroutines-android",version = "1.3.3")
    implementation(DepLibrary.APPCOMPAT)
    implementation(DepLibrary.CORE_KTX)
    implementation(DepLibrary.COROUTINES_ANDROID)
    implementation(DepLibrary.MATERIAL)
    implementation(DepLibrary.CONSTRAINT_LAYOUT)
    implementation(DepLibrary.RECYCLER_VIEW)

    implementation(DepLibrary.NAVIGATION_FRAGMENT_KTX)
    implementation(DepLibrary.NAVIGATION_UI_KTX)
//    implementation(project(mapOf("path" to ":booster")))
    implementation("com.github.zhiwei1990:KtBooster:0.1.0-alpha01")

    //test dependencies，使用的是buildSrc下面的自定义扩展
    addTestDependencies()
}