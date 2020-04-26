package org.zhiwei.libnet.config

import android.util.Log
import okhttp3.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月25日 19:56
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于记录okHttp的网络日志的拦截器
 */
class KtHttpLogInterceptor(block: (KtHttpLogInterceptor.() -> Unit)? = null) : Interceptor {

    private var logLevel: LogLevel = LogLevel.NONE//打印日期的标记
    private var colorLevel: ColorLevel = ColorLevel.DEBUG//默认是debug级别的logcat
    private var logTag = TAG//日志的Logcat的Tag

    init {
        block?.invoke(this)
    }

    /**
     * 设置LogLevel
     */
    fun logLevel(level: LogLevel): KtHttpLogInterceptor {
        logLevel = level
        return this
    }

    /**
     * 设置colorLevel
     */
    fun colorLevel(level: ColorLevel): KtHttpLogInterceptor {
        colorLevel = level
        return this
    }

    /**
     * 设置colorLevel
     */
    fun logTag(tag: String): KtHttpLogInterceptor {
        logTag = tag
        return this
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        //请求
        val request = chain.request()
        //响应
        return kotlin.runCatching { chain.proceed(request) }
            .onFailure {
                it.printStackTrace()
                logIt(
                    it.message.toString(),
                    ColorLevel.ERROR
                )
            }.onSuccess { response ->
                if (logLevel == LogLevel.NONE) {
                    return response
                }
                //记录请求日志
                logRequest(request, chain.connection())
                //记录响应日志
                logResponse(response)
            }.getOrThrow()
    }

    /**
     * 记录请求日志
     */
    private fun logRequest(request: Request, connection: Connection?) {
        val sb = StringBuilder()
        sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        when (logLevel) {
            LogLevel.NONE -> {
                /*do nothing*/
            }
            LogLevel.BASIC -> {
                sb.appendln("请求 method: ${request.method} url: ${request.url} tag: ${request.tag()} protocol: ${connection?.protocol() ?: Protocol.HTTP_1_1}")
            }
            LogLevel.HEADERS -> {
                val headersStr = request.headers.joinToString { header ->
                    "请求 Header: {${header.first}=${header.second}}\n"
                }
                sb.appendln(headersStr)
            }
            LogLevel.BODY -> {
                val headersStr = request.headers.joinToString { header ->
                    "请求 Header: {${header.first}=${header.second}}\n"
                }
                sb.appendln(headersStr)
                    .appendln(request.body)
            }
        }
        sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        logIt(sb)
    }

    /**
     * 记录响应日志
     * [response] 响应数据
     */
    private fun logResponse(response: Response) {
        val sb = StringBuilder()
        sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        when (logLevel) {
            LogLevel.NONE -> {
                /*do nothing*/
            }
            LogLevel.BASIC -> {
                sb.appendln("响应 protocol: ${response.protocol} code: ${response.code} message: ${response.message} url: ${response.request.url}")
                sb.appendln(
                    "响应 sentRequestTime: ${toDateTimeStr(
                        response.sentRequestAtMillis,
                        MILLIS_PATTERN
                    )} receivedResponseTime: ${toDateTimeStr(
                        response.receivedResponseAtMillis,
                        MILLIS_PATTERN
                    )}"
                )
            }
            LogLevel.HEADERS -> {
                val headersStr = response.headers.joinToString { header ->
                    "响应 Header: {${header.first}=${header.second}}\n"
                }
                sb.appendln(headersStr)
            }
            LogLevel.BODY -> {
                val headersStr = response.headers.joinToString { header ->
                    "响应 Header: {${header.first}=${header.second}}\n"
                }
                sb.appendln(headersStr)
                val bodyStr = kotlin.runCatching {
                    response.body?.string()
                }.getOrNull()
                sb.appendln(bodyStr)
            }
        }
        sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        logIt(sb)
    }

    /**
     * 打印日志
     * [any]需要打印的数据对象
     * [tempLevel],便于临时调整打印color等级
     */
    private fun logIt(any: Any, tempLevel: ColorLevel? = null) {
        when (tempLevel ?: colorLevel) {
            ColorLevel.VERBOSE -> Log.v(logTag, any.toString())
            ColorLevel.DEBUG -> Log.d(logTag, any.toString())
            ColorLevel.INFO -> Log.i(logTag, any.toString())
            ColorLevel.WARN -> Log.w(logTag, any.toString())
            ColorLevel.ERROR -> Log.e(logTag, any.toString())
        }
    }


    companion object {
        private const val TAG = "<KtHttp>"//默认的TAG

        //时间格式化
        const val MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSXXX"

        //转化为格式化的时间字符串
        fun toDateTimeStr(millis: Long, pattern: String): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(millis)
        }
    }


    /**
     * 打印日志的范围
     */
    enum class LogLevel {
        NONE,//不打印
        BASIC,//纸打印行首，请求/响应
        HEADERS,//打印请求和响应的 所有 header
        BODY,//打印所有
    }

    /**
     * Log颜色等级，应用于Android Logcat分为 v、d、i、w、e
     */
    enum class ColorLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

}