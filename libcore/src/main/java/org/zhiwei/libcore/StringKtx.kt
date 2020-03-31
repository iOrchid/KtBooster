package org.zhiwei.libcore

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月19日 13:56
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * String相关的扩展类
 */

//region String 扩展工具类

/**
 * 显示toast提示
 * [context] Context上下文
 * [duration] Toast.LENGTH_SHORT 或者 Toast。LENGTH_LONG，也就是 0 或 1
 */
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

/**
 * String打印日志 Debug级别
 * [tag] 当前打印日志的文件类，用作TAG，默认
 */
fun String.logD(tag: Any) {
    if (tag is String) {
        Log.d(tag, this)
    } else {
        Log.d(tag.javaClass.simpleName, this)
    }
}

/**
 * String打印日志 Info级别
 * [tag] 当前打印日志的文件类，用作TAG，默认
 */
fun String.logI(tag: Any) {
    if (tag is String) {
        Log.i(tag, this)
    } else {
        Log.i(tag.javaClass.simpleName, this)
    }
}

/**
 * String打印日志 Warn级别
 * [tag] 当前打印日志的文件类，用作TAG，默认
 */
fun String.logW(tag: Any) {
    if (tag is String) {
        Log.w(tag, this)
    } else {
        Log.w(tag.javaClass.simpleName, this)
    }
}

/**
 * String打印日志 Error级别
 * [tag] 当前打印日志的文件类，用作TAG，默认
 */
fun String.logE(tag: Any) {
    if (tag is String) {
        Log.e(tag, this)
    } else {
        Log.e(tag.javaClass.simpleName, this)
    }
}

//endregion