package org.zhiwie.libnet

import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.experimental.theories.suppliers.TestedOn
import org.zhiwie.libnet.config.KtHttpLogInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月22日 21:19
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 简单配置的OkHttp封装，用于项目网络请求
 * todo 1、管理状态，取消；2、协程配合，retrofit；3、完善封装特性；
 */
object HttpApi {

    private const val TAG = "HttpApi"


    //url的配置 以/结尾，erpath就不用/开始了
    private var baseUrl: String? = null

    //okHttpClient对象构建配置
    private val okClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
        .connectTimeout(10, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(10, TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)
        .cookieJar(CookieJar.NO_COOKIES)
        .addNetworkInterceptor(KtHttpLogInterceptor())//添加网络拦截器，可以对okhttp的网络请求做拦截处理，不同于应用拦截器，这里能感知所有网络状态，比如重定向。
        .build()

    //gson对象，免得每次都创建
    private val gson = Gson()

    /**
     * 获取okHttpClient对象
     */
    fun getClient() = okClient

    /**
     * 配置server的根url地址,也可以自定义okClient
     */
    fun initConfig(@NonNull baseUrl: String, builder: () -> OkHttpClient? = { okClient }): HttpApi {
        HttpApi.baseUrl = baseUrl
        return this
    }

    //region get请求

    /**
     * get请求服务器数据,异步请求，接口回调形式
     * [data]为map形式的key -- value 请求参数，或者别的形式
     */
    fun get(data: Map<String, String>?, @NonNull path: String, callback: IHttpCallback) {
        val request = buildGetRequest(path, data)
        okClient.newCall(request)
            .enqueue(callback(callback))
    }

    /**
     * get请求服务器数据,异步请求，liveData回调形式
     * [data]为map形式的key -- value 请求参数，或者别的形式
     */
    fun get(data: Map<String, String>?, @NonNull path: String, liveData: MutableLiveData<String?>) {
        val request = buildGetRequest(path, data)

        okClient.newCall(request)
            .enqueue(liveBack(liveData))
    }

    /**
     * get请求服务器数据,异步请求，lambda回调形式
     * [data]为map形式的key -- value 请求参数，或者别的形式
     */
    fun get(data: Map<String, String>?, @NonNull path: String, method: (String?) -> Unit) {
        val request = buildGetRequest(path, data)

        okClient.newCall(request)
            .enqueue(lambdaInvoke(method))
    }

    /**
     * 构建get请求request
     * [params]用于拼接url中的请求key--value，可以没有
     * [path]请求地址path 用于和baseUrl拼接成完整请求url
     */
    private fun buildGetRequest(
        @NonNull path: String,
        @Nullable params: Map<String, String>? = null
    ): Request {
        //如果baseUrl没有配置，则抛异常，提示,后期可以更严格的正则匹配url为合法网址
        baseUrl ?: throw IllegalArgumentException("BaseUrl必须要初始化配置正确，方可正常使用HttpApi")

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

    //endregion

    //region post请求，异步 gson方式

    /**
     * post请求服务器数据，异步请求
     * 普通的接口回调
     */
    fun post(any: Any?, @NonNull path: String, callback: IHttpCallback) {
        val request = buildJsonPost(any, path)

        okClient.newCall(request)
            .enqueue(callback(callback))
    }


    /**
     * post请求服务器数据，异步请求
     * 使用lambda形式的函数回调
     */
    fun post(any: Any?, @NonNull path: String, method: (String?) -> Unit) {
        val request = buildJsonPost(any, path)

        okClient.newCall(request)
            .enqueue(lambdaInvoke(method))
    }

    /**
     * post请求服务器数据，异步请求
     * 使用liveData作为回调
     */
    fun post(any: Any?, @NonNull path: String, liveData: MutableLiveData<String?>) {
        val request = buildJsonPost(any, path)

        okClient.newCall(request)
            .enqueue(liveBack(liveData))
    }


    /**
     * 构建post的请求request
     * [path]请求的service
     * [any] 请求数据对象
     */
    private fun buildJsonPost(any: Any?, @NonNull path: String): Request {
        //如果baseUrl没有配置，则抛异常，提示,后期可以更严格的正则匹配url为合法网址
        baseUrl ?: throw IllegalArgumentException("BaseUrl必须要初始化配置正确，方可正常使用HttpApi")

        val url = if (path.startsWith(
                "http://",
                true
            ) || path.startsWith("Https://")
        ) path else "$baseUrl$path"

        //todo mediaType 可参照 https://tool.oschina.net/commons
        val mediaType =
            "application/json; charset=utf-8".toMediaTypeOrNull()//将body数据，转化为json的string
        val data = gson.toJson(any)//如果data为null，会得到"null"的字符串
        return Request.Builder()
            .url(url)
            .post(data.toRequestBody(mediaType))
            .tag(any)
            .build()
    }

    //endregion


    /**
     * 普通使用接口形式的[callback]回调
     */
    private fun callback(callback: IHttpCallback): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "${e.message}")
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSucceeded(response.body?.string())
                Log.i(TAG, "ResponseBodyString: ${response.body?.string()}")

            }
        }
    }

    /**
     * liveData回调方式
     */
    private fun liveBack(callback: MutableLiveData<String?>): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "${e.message}")
                callback.postValue(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.postValue(response.body?.string())
                Log.i(TAG, "ResponseBodyString: ${response.body?.string()}")

            }
        }
    }


    /**
     * 使用Kotlin函数语法特性的invoke方式回调
     */
    private fun lambdaInvoke(callback: (String?) -> Unit): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "${e.message}")
                callback.invoke(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response.body?.string())
                Log.i(TAG, "ResponseBodyString: ${response.body?.string()}")

            }
        }
    }

}