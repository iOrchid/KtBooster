/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月23日 11:12
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | "_ \ / _` | "__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * kotlin dsl 配置构建依赖库的统一管理
 */
object DepLibrary {


    //<editor-folder desc="平台基础配置库">
    const val KOTLIN_LIB_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    const val KOTLIN_LIB_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
    const val KOTLIN_LIB = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${VersionExt.coroutines}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${VersionExt.coroutines}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${VersionExt.compat}"
    const val COLLECTION = "androidx.collection:collection:${VersionExt.collection}"
    const val CORE_KTX = "androidx.core:core-ktx:${VersionExt.androidxCoreKtx}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${VersionExt.fragmentKtx}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${VersionExt.activityKtx}"
    const val COLLECTION_KTX = "androidx.collection:collection-ktx:${VersionExt.collection}"
    const val PALETTE_KTX = "androidx.palette:palette-ktx:${VersionExt.paletteKtx}"
    const val SQLITE_KTX = "androidx.sqlite:sqlite-ktx:${VersionExt.sqliteKtx}"
    const val MATERIAL = "com.google.android.material:material:${VersionExt.material}"

    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${VersionExt.constraint}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${VersionExt.recyclerView}"

    //<editor-folder desc="Jetpack配置Libs">
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata:${VersionExt.lifecycle}"


    //</editor-folder>
    const val LIFECYCLE_RX_STREAM_KTX =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:${VersionExt.lifecycle}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel:${VersionExt.lifecycle}"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime:${VersionExt.lifecycle}"
    const val LIFECYCLE_LIVEDATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${VersionExt.lifecycle}"
    const val LIFECYCLE_VIEWMODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${VersionExt.lifecycle}"
    const val LIFECYCLE_COMMON_JAVA8 =
        "androidx.lifecycle:lifecycle-common-java8:${VersionExt.lifecycle}"//用于kapt

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${VersionExt.room}"
    const val ROOM_COMMON = "androidx.room:room-common:${VersionExt.room}"
    const val ROOM_KTX = "androidx.room:room-ktx:${VersionExt.room}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${VersionExt.room}"//用于kapt

    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${VersionExt.paging}"
    const val PAGING_RUNTIME_KTX = "androidx.paging:paging-runtime-ktx:${VersionExt.paging}"
    const val PAGING_COMMON = "androidx.paging:paging-common:${VersionExt.paging}"
    const val PAGING_COMMON_KTX = "androidx.paging:paging-common-ktx:${VersionExt.paging}"

    const val WORK_RUNTIME = "androidx.work:work-runtime:${VersionExt.work}"
    const val WORK_RUNTIME_KTX = "androidx.work:work-runtime-ktx:${VersionExt.work}"

    //注意配置navigation版本号的时候，project下的build.gradle的class path也需要同步navigation的版本号配置
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui:$navigation"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:$navigation"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment:$navigation"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:$navigation"
    const val NAVIGATION_RUNTIME = "androidx.navigation:navigation-runtime:$navigation"

    //</editor-folder>


    //<editor-folder desc="Github第三方的优秀开源项目">

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${VersionExt.retrofit}"
    const val RETROFIT_CONVERTER_GSON =
        "com.squareup.retrofit2:converter-gson:${VersionExt.retrofit}"

    const val OKHTTP = "com.squareup.okhttp3:okhttp:${VersionExt.okhttp}"
    const val MOCK_OKHTTP = "com.squareup.okhttp3:mockwebserver:${VersionExt.okhttp}"

    const val GSON = "com.google.code.gson:gson:${VersionExt.gson}"

    //腾讯开源key-value的高性能存储方案
    const val MMKV = "com.tencent:mmkv-static:${VersionExt.mmkv}"
    //</editor-folder>


    //<editor-folder desc="Test">

    const val JUNIT = "junit:junit:${VersionExt.junit}"
    const val JUNIT_KTX = "androidx.test.ext:junit-ktx:${VersionExt.junitKtx}"
    const val TEST_CORE = "androidx.test:core:${VersionExt.testCore}"
    const val TEST_CORE_KTX = "androidx.test:core-ktx:${VersionExt.testCoreKtx}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${VersionExt.espressoCore}"

    const val TEST_RULES = "androidx.test:rules:${VersionExt.testRules}"
    const val TEST_RUNNER = "androidx.test:runner:${VersionExt.testRunner}"
    const val COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${VersionExt.coroutines}"

    const val ROBOLECTRIC = "org.robolectric:robolectric:${VersionExt.robolectric}"//自动化测试依赖库

    //</editor-folder>


}