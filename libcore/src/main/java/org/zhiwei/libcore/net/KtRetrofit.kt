package org.zhiwei.libcore.net

import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.zhiwei.libcore.net.config.HostInterceptor
import org.zhiwei.libcore.net.config.KtHttpLogInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Retrofit的简单封装
 */
object KtRetrofit {

    //定义函数，用于获取动态url的函数
    private var retrofit: Retrofit? = null//retrofit 请求client

    /**
     * 初始化配置
     * [baseUrl]项目接口的基类url，以/结尾
     * [okClient] 可以指定okHttp的client
     * [url] 动态生成可替换的baseUrl的函数
     */
    fun initConfig(
        baseUrl: String,
        okClient: OkHttpClient? = null,
        url: () -> String = { baseUrl }
    ): KtRetrofit {
        val client = okClient ?: genClient(url)
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return this
    }

    /**
     * 创建okHttpClient对象
     */
    private fun genClient(url: () -> String): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
            .connectTimeout(10, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
            .readTimeout(10, TimeUnit.SECONDS)//读取服务器返回数据的时长
            .writeTimeout(10, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
            .retryOnConnectionFailure(true)//重连
            .followRedirects(false)//重定向
            .cookieJar(CookieJar.NO_COOKIES)
            .addInterceptor(HostInterceptor(url))
//        .addNetworkInterceptor(HeaderInterceptor())//公共header的拦截器
            .addNetworkInterceptor(KtHttpLogInterceptor {
                logLevel(KtHttpLogInterceptor.LogLevel.BODY)
            })
            .build()
    }

    /**
     * 获取retrofit的Service对象
     * [serviceClazz] 定义的retrofit的service 接口类
     * [T] 类型
     */
    fun <T> getService(serviceClazz: Class<T>): T {
        return retrofit?.create(serviceClazz) ?: error("Retrofit必须初始化，且配置BaseURL")
    }

}