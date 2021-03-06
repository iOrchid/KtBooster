plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    //2、发布到jitpack.io的步骤2,似乎所有依赖module都要配置
    id("maven-publish")
}

repositories {
    mavenCentral()
    google()
    jcenter()
    maven(url = "https://jitpack.io")
}

group = "com.github.zhiwei1990"
version = "0.2.1"

setupLibraryModule()

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    implementation(DepLibrary.KOTLIN_LIB)
    implementation(DepLibrary.KOTLIN_LIB_JDK)
    implementation(DepLibrary.KOTLIN_LIB_REFLECT)
    implementation(DepLibrary.COROUTINES_ANDROID)

    implementation(DepLibrary.APPCOMPAT)
    implementation(DepLibrary.CORE_KTX)
    implementation(DepLibrary.ACTIVITY_KTX)
    implementation(DepLibrary.FRAGMENT_KTX)

    implementation(DepLibrary.MATERIAL)
    implementation(DepLibrary.RECYCLER_VIEW)
    implementation(DepLibrary.CONSTRAINT_LAYOUT)
    implementation(DepLibrary.VIEW_PAGE2)

    //jetpack
    kapt(DepLibrary.LIFECYCLE_COMMON_JAVA8)
    implementation(DepLibrary.LIFECYCLE_LIVEDATA_KTX)
    implementation(DepLibrary.LIFECYCLE_VIEWMODEL_KTX)

    implementation(DepLibrary.ROOM_COMMON)
    kapt(DepLibrary.ROOM_COMPILER)
    implementation(DepLibrary.ROOM_KTX)

    implementation(DepLibrary.PAGING_COMMON_KTX)
    implementation(DepLibrary.PAGING_RUNTIME_KTX)

    implementation(DepLibrary.WORK_RUNTIME_KTX)

    implementation(DepLibrary.NAVIGATION_FRAGMENT_KTX)
    implementation(DepLibrary.NAVIGATION_UI_KTX)



    implementation(DepLibrary.RETROFIT)
    implementation(DepLibrary.RETROFIT_CONVERTER_GSON)
    implementation(DepLibrary.GSON)

    implementation(DepLibrary.OKHTTP)
    implementation(DepLibrary.MMKV)
    implementation(DepLibrary.COIL)

    // 滴滴助手
    debugImplementation(DepLibrary.DOKIT)
    releaseImplementation(DepLibrary.DOKIT_NO_OP)

    api(DepLibrary.DIALOGS_CORE)
    api(DepLibrary.DIALOGS_INPUT)
}