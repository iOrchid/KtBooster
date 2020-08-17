/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月23日 14:03
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | "_ \ / _` | "__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于项目project的统一管理，定义的一些扩展函数，或 常量字段
 */

//region build config
const val kotlin_version = "1.3.72"
const val compileSdkNum = 29
const val buildToolsNum = "29.0.3"
const val minSdkNum = 21
const val targetSdkNum = 29
const val libCode = 3
const val libVersion = "0.1.3"//这里的版本号和发布jitpack.io的版本号不一样，那个是github Release页面的

//endregion

const val navigation = "2.2.1"//navigation 版本号，需要在dependencies和classpath保持一致

//依赖库的相关版本库
object VersionExt {

    //region android platform
    internal const val coroutines = "1.3.8"
    internal const val compat = "1.1.0"
    internal const val paletteKtx = "1.0.0"
    internal const val collection = "1.1.0"
    internal const val androidxCoreKtx = "1.3.1"
    internal const val fragmentKtx = "1.2.4"
    internal const val activityKtx = "1.1.0"
    internal const val material = "1.1.0"
    internal const val sqliteKtx = "2.1.0"
    internal const val constraint = "2.0.0-beta8"
    internal const val recyclerView = "1.1.0"

    internal const val concurrentFutures = "1.0.0"
    internal const val concurrentFuturesKtx = "1.1.0-rc01"

    //endregion

    //region jetPack
    internal const val lifecycle = "2.2.0"
    internal const val room = "2.2.5"
    internal const val paging = "2.1.2"
    internal const val work = "2.4.0"
    //endregion

    //region Github excellence project
    internal const val retrofit = "2.9.0"
    internal const val gson = "2.8.6"
    internal const val okhttp = "4.8.0"
    internal const val mmkv = "1.2.1"

    //endregion


    //region test
    internal const val junit = "4.13"
    internal const val junitKtx = "1.1.1"
    internal const val testCore = "1.2.0"
    internal const val testCoreKtx = "1.2.0"
    internal const val espressoCore = "3.2.0"
    internal const val testRules = "1.2.0"
    internal const val testRunner = "1.2.0"
    internal const val robolectric = "4.3.1"
    //endregion

}