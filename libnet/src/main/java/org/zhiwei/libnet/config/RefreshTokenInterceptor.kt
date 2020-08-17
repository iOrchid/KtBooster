package org.zhiwei.libnet.config

import okhttp3.Interceptor
import okhttp3.Response


/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于接口请求时候，登录状态校验的拦截器
 */
class RefreshTokenInterceptor : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {

        /*
         * 逻辑要点：
         * 0、同步锁配置，避免多个接口的时候，单个处理，造成浪费和不同步结果
         * 1、配置需要校验登录的接口
         * 2、判断response的响应码
         * 3、http的code，校验token是否过期
         * 4、已过期就刷新请求登录token的接口，同步线程操作
         * 5、刷新后的token更新到公共参数value上，继续之前发起校验前的请求，
         * 6、若是token刷新失败，放过该请求接口，使之服务端返回错误信息
         */
        //替换参数的方式

        //对请求的url操作，可以添加公共参数，
        val request = chain.request()
        val httpUrl = request.url.newBuilder()
            .setEncodedQueryParameter("pub_key", "pub_value")
            .build()
        //使用原request构建新的request
        val newReq = request.newBuilder()
            .method(request.method, request.body)
            .url(httpUrl)
            .build()
        return chain.proceed(newReq)
    }

}