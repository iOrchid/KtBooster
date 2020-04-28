plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    //2、发布到jitpack.io的步骤2,似乎所有依赖module都要配置
    id("com.github.dcendents.android-maven")
}

android {
    compileSdkVersion(compileSdkNum)
    buildToolsVersion(buildToolsNum)

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

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation(DepLibrary.COROUTINES_ANDROID)
    implementation(DepLibrary.COLLECTION)
    implementation(DepLibrary.LIFECYCLE_LIVEDATA)
    implementation(DepLibrary.LIFECYCLE_LIVEDATA_KTX)

    //retrofit okhttp gson
    implementation(DepLibrary.RETROFIT)
    implementation(DepLibrary.RETROFIT_CONVERTER_GSON)

    api(DepLibrary.OKHTTP)
    implementation(DepLibrary.MOCK_OKHTTP)

    implementation(DepLibrary.GSON)


    //test
    addTestDependencies()
}
