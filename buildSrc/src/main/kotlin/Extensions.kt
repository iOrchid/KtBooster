
/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2021年02月07日 14:03
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于项目project的统一管理，定义的一些扩展函数，或 常量字段
 */

//region build config
const val kotlin_version = "1.4.31"
const val compileSdkNum = 30
const val buildToolsNum = "30.0.3"
const val minSdkNum = 21
const val targetSdkNum = 29
const val libCode = 4
const val libVersion = "0.2"

//endregion

const val navigation = "2.3.3"//navigation 版本号，需要在dependencies和classpath保持一致

//依赖库的相关版本库
object VersionExt {

    //region android platform
    internal const val coroutines = "1.4.2"
    internal const val compat = "1.2.0"
    internal const val paletteKtx = "1.0.0"
    internal const val collection = "1.1.0"
    internal const val androidxCoreKtx = "1.3.2"
    internal const val fragmentKtx = "1.2.5"
    internal const val activityKtx = "1.1.0"
    internal const val material = "1.2.1"
    internal const val sqliteKtx = "2.1.0"
    internal const val constraint = "2.0.4"
    internal const val recyclerView = "1.1.0"
    internal const val viewPager2 = "1.0.0"

    internal const val concurrentFuturesKtx = "1.1.0"

    //endregion

    //region jetPack
    internal const val lifecycle = "2.3.0"
    internal const val room = "2.2.6"
    internal const val paging = "3.0.0-beta01"
    internal const val work = "2.5.0"
    internal const val startup = "1.0.0"
    //endregion

    //region Github excellence project
    internal const val retrofit = "2.9.0"
    internal const val gson = "2.8.6"
    internal const val okhttp = "4.9.0"
    internal const val mmkv = "1.2.6"

    internal const val coil = "1.1.1"
    internal const val utils = "1.30.5"
    internal const val liveBus = "1.7.2"
    internal const val agentWeb = "4.1.4"
    internal const val BUGLY = "3.3.3"

    internal const val doKit = "3.3.5"
    internal const val dialogs = "3.3.0"
    //endregion


    //region test

    internal const val junit = "4.13.1"
    internal const val junitKtx = "1.1.2"
    internal const val testCore = "1.3.0"
    internal const val testCoreKtx = "1.3.0"
    internal const val espressoCore = "3.3.0"
    internal const val testRules = "1.3.0"
    internal const val testRunner = "1.3.0"
    internal const val robolectric = "4.5.1"

    //endregion

}