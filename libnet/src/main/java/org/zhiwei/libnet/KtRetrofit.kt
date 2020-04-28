package org.zhiwei.libnet

import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.zhiwei.libnet.config.KtHttpLogInterceptor
import org.zhiwei.libnet.config.RetryInterceptor
import org.zhiwei.libnet.support.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月28日 11:45
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 封装的retrofit请求类
 */
object KtRetrofit {

    var maxRetry = 0//最大重试 次数

    private val okClient = OkHttpClient.Builder()
        .callTimeout(20, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
        .connectTimeout(20, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(20, TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(20, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)//自定义重试次数，需要自定义拦截器 可以扩展函数retryWhen操作符
        .cookieJar(CookieJar.NO_COOKIES)
        .addNetworkInterceptor(
            KtHttpLogInterceptor {
                logLevel(KtHttpLogInterceptor.LogLevel.BODY)
                colorLevel(KtHttpLogInterceptor.ColorLevel.DEBUG)
            })//添加网络日志拦截器
        .addNetworkInterceptor(RetryInterceptor(maxRetry))
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(okClient)

    private var retrofit: Retrofit? = null//retrofit 请求client

    /**
     * 初始化配置
     * [baseUrl]项目接口的基类url，以/结尾
     */
    fun initConfig(baseUrl: String): KtRetrofit {
        retrofit = retrofitBuilder.baseUrl(baseUrl).build()
        return this
    }

    /**
     * 获取retrofit的Service对象
     * [serviceClazz] 定义的retrofit的service 接口类
     */
    fun <T> getService(serviceClazz: Class<T>): T {
        if (retrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化，需要配置baseURL")
        } else {
            return this.retrofit!!.create(serviceClazz)
        }
    }
}