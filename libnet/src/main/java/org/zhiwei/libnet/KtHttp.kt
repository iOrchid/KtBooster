package org.zhiwei.libnet

import android.net.Uri
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.zhiwei.libnet.config.KtHttpLogInterceptor
import org.zhiwei.libnet.config.RetryInterceptor
import org.zhiwei.libnet.support.toLiveData
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月25日 17:06
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 高阶的okHttp配合coroutines的用法
 */
object KtHttp {

    var maxRetry = 0//最大重试 次数

    //url的配置 以/结尾，而path就不用/开始了
    private var baseUrl: String? = null

    //请求对象的map，用于取消
    val callMap = hashMapOf<Any?, Call>()

    //okHttpClient对象构建配置
    private var defaultClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
        .connectTimeout(10, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(10, TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)
        .cookieJar(CookieJar.NO_COOKIES)
        .addNetworkInterceptor(KtHttpLogInterceptor {
            logLevel(KtHttpLogInterceptor.LogLevel.BODY)
        })//添加网络拦截器，可以对okHttp的网络请求做拦截处理，不同于应用拦截器，这里能感知所有网络状态，比如重定向。
        .addNetworkInterceptor(RetryInterceptor(maxRetry))
        .build()

    //可公开的okHttpClient
    var okClient = defaultClient

    //Gson对象，免得每次都创建
    private val gson = Gson()


    /**
     * 配置server的根url地址,也可以自定义okClient
     * [baseUrl] 项目接口的baseUrl
     * [client] 函数参数，创建okHttpClient对象
     */
    fun initConfig(
        @NonNull baseUrl: String,
        client: () -> OkHttpClient = { defaultClient }
    ): KtHttp {
        this.baseUrl = baseUrl
        this.defaultClient = client.invoke()
        return this
    }

    //region 构建请求request


    /**
     * 构建get请求request
     * [params]用于拼接url中的请求key--value，可以没有
     * [path]请求地址path 用于和baseUrl拼接成完整请求url
     */
    fun buildGetRequest(
        @NonNull path: String,
        @Nullable params: Map<String, String>? = null
    ): Request {
        checkBaseUrl()
        //便于对不是baseUrl的链接做接受;下面的处理方式可能不是最佳，也可以方法签名中默认参数flag标记是否独立url
        var url = if (path.startsWith(
                "http://",
                true
            ) || path.startsWith("Https://")
        ) path else "$baseUrl$path"
        //有get请求参数
        if (!params.isNullOrEmpty()) {
            val sb = StringBuilder()
            with(sb) {
                append("$url?")
                params.forEach { item ->
                    append("${item.key}=${item.value}&")
                }
            }
            url = sb.toString().dropLast(1)//上面的拼接，会在最后多一个&符号，所去掉
        }
        return Request.Builder()
            .url(url)
            .cacheControl(CacheControl.FORCE_NETWORK)
            .tag(params)
            .build()

    }

    /**
     * 构建post的请求request
     * [path]请求的service
     * [body] 请求数据对象
     */
    fun buildJsonPost(@NonNull path: String, body: Any?): Request {
        checkBaseUrl()
        val url = if (path.startsWith(
                "http://",
                true
            ) || path.startsWith("Https://")
        ) path else "$baseUrl$path"

        //todo mediaType 可参照 https://tool.oschina.net/commons
        val mediaType =
            "application/json; charset=utf-8".toMediaTypeOrNull()//将body数据，转化为json的string
        val data = gson.toJson(body)//如果data为null，会得到"null"的字符串

        return Request.Builder()
            .url(url)
            .post(data.toRequestBody(mediaType))
            .tag(data)
            .build()
    }

    /**
     * 校验检查baseUrl
     */
    private fun checkBaseUrl() {
        //如果baseUrl没有配置，则抛异常，提示,后期可以更严格的正则匹配url为合法网址
        baseUrl ?: throw IllegalArgumentException("BaseUrl必须要初始化配置正确，方可正常使用KtHttp")
        Uri.parse(baseUrl).scheme
            ?: throw IllegalArgumentException("BaseUrl格式不合法,请检查是否有scheme")
    }

    //endregion

    /**
     * get请求
     * [path] 基于baseUrl之后的请求path，
     * [params] get请求的参数key，value的map对象，个别情况的请求params可为空
     */
    fun get(path: String, params: Map<String, String>? = null) = runBlocking {
        kotlin.runCatching {
            val request = buildGetRequest(
                path,
                params
            )
            val newCall = okClient.newCall(request)
            callMap[request.tag()] = newCall

            newCall.call()
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
    }

    /**
     * get请求 返回LiveData
     * [path] 基于baseUrl之后的请求path，
     * [params] get请求的参数key，value的map对象，个别情况的请求params可为空
     * 可能会有异常，比如断网，超时之类的
     */
    inline fun <reified T> get(path: String, params: Map<String, String>? = null): LiveData<T?>? {
        return kotlin.runCatching {
            val request = buildGetRequest(
                path,
                params
            )
            val newCall = okClient.newCall(request)
            this.callMap[request.tag()] = newCall
            newCall
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()?.toLiveData<T>()
    }

    /**
     * post请求
     * [path] 基于baseUrl之后的请求path，
     * [body] post请求的数据对象,个别情况的请求body可为空
     */
    fun post(path: String, body: Any? = null) = runBlocking {
        kotlin.runCatching {
            val request = buildJsonPost(path, body)
            val newCall = okClient.newCall(request)
            callMap[request.tag()] = newCall
            newCall.call()
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
    }

    /**
     * post请求 返回liveData形式
     * [path] 基于baseUrl之后的请求path，
     * [body] post请求的数据对象,个别情况的请求body可为空
     * 可能会有异常，比如断网，超时之类的
     */
    inline fun <reified T> post(path: String, body: Any? = null): LiveData<T?>? {
        return kotlin.runCatching {
            val request = buildJsonPost(path, body)
            val newCall = okClient.newCall(request)
            callMap[request.tag()] = newCall
            newCall
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()?.toLiveData<T>()
    }


    /**
     * 取消网络请求，tag就是每次请求的id 标记，也就是请求的传参
     */
    fun cancel(tag: Any) {
        callMap[tag]?.cancel()
    }

    /**
     * 取消所有网络请求
     */
    fun cancelAll() {
        callMap.forEach {
            it.value.cancel()
        }
    }

    /**
     * 自定义扩展函数，扩展okHttp的Call的异步执行方式，结合coroutines，返回DataResult的数据响应
     * 也可以使用resumeWith返回的是Result<T>
     * [async] 默认是异步调用，可选参数，false的话就是同步调用
     */
    private suspend fun Call.call(async: Boolean = true): Response {
        return suspendCancellableCoroutine { continuation ->
            if (async) {
                enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        //避免不必要的冗余调用
                        if (continuation.isCancelled) return
                        continuation.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        continuation.resume(response)
                    }
                })
            } else {
                continuation.resume(execute())
            }
            continuation.invokeOnCancellation {
                try {
                    cancel()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }


    fun Response.serial(vararg block: () -> Response?) {

    }

    /**
     * 并行请求，执行多个请求，放在同一代码块
     */
    fun parallel(block: KtHttp.() -> Unit) {
        block.invoke(this)
    }
}
 