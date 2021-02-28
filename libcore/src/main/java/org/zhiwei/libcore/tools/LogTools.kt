package org.zhiwei.libcore.tools

import android.util.Log


/*
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 */
object LogTools {

    //region Log工具类 私有属性

    private const val DEFAULT_TAG = "LogKt"

    //endregion

    //region 公开属性
    var decorateMsg = true//log消息添加------包裹显示，可以关闭，则简单化显示
    var enable = true//是否输出，默认true，可以release时候设置false
    //endregion


    /**
     * 打印日志 Debug级别
     */
    fun v(message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_VERBOSE, "$message", tr)
    }

    fun v(tag: String, message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_VERBOSE, "$message", tr, tag)
    }

    /**
     * 打印日志 Debug级别
     */
    fun d(message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_DEBUG, "$message", tr)
    }

    fun d(tag: String, message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_DEBUG, "$message", tr, tag)
    }

    /**
     * 打印日志 Info级别
     */
    fun i(message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_INFO, "$message", tr)
    }

    fun i(tag: String, message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_INFO, "$message", tr, tag)
    }

    /**
     * 打印日志 Warn级别
     */
    fun w(message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_WARN, "$message", tr)
    }

    fun w(tag: String, message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_WARN, "$message", tr, tag)
    }

    /**
     * 打印日志 Error级别
     */
    fun e(message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_ERROR, "$message", tr)
    }

    fun e(tag: String, message: Any, tr: Throwable? = null) {
        if (enable) printIt(PRIORITY_ERROR, "$message", tr, tag)
    }

    //Log级别
    private const val PRIORITY_VERBOSE = 2
    private const val PRIORITY_DEBUG = 3
    private const val PRIORITY_INFO = 4
    private const val PRIORITY_WARN = 5
    private const val PRIORITY_ERROR = 6
    private const val PRIORITY_ASSERT = 7

    //打印
    private fun printIt(
        priority: Int,
        msg: String,
        tr: Throwable? = null,
        tag: String = DEFAULT_TAG
    ) {
        //方法栈，想要显示出对外使用的时候，调用点的栈区信息，除去该工具类内部的函数栈
        val stackTrace = Thread.currentThread().stackTrace
        val innerLastIndex = stackTrace.indexOfLast { it.className == LogTools::class.java.name }
        val outCallInfo = stackTrace[innerLastIndex + 1]

        val message = if (decorateMsg) """
${outCallInfo.fileName}-->${outCallInfo.className.substringAfterLast('.')}-->${outCallInfo.methodName}-->LineNum:${outCallInfo.lineNumber}
--------------------------------------------------------------------------------------------------------------------------------------
>>
>>  (づ￣ 3￣)づ     $msg                                                                          ✪ ω ✪
>>
--------------------------------------------------------------------------------------------------------------------------------------
            """.trimIndent() else """
${outCallInfo.fileName}-->${outCallInfo.className}-->${outCallInfo.methodName}-->LineNum:${outCallInfo.lineNumber}
$msg""".trimIndent()
        when (priority) {
            PRIORITY_VERBOSE -> {
                Log.v(tag, message, tr)
            }
            PRIORITY_DEBUG -> {
                Log.d(tag, message, tr)
            }
            PRIORITY_INFO -> {
                Log.i(tag, message, tr)
            }
            PRIORITY_WARN -> {
                Log.w(tag, message, tr)
            }
            PRIORITY_ERROR -> {
                Log.e(tag, message, tr)
            }
            PRIORITY_ASSERT -> {
                Log.wtf(tag, message, tr)
            }
            else -> Log.v(tag, message, tr)

        }
    }

}