package org.zhiwei.libcore.net.config

import androidx.core.net.toUri
import okhttp3.Interceptor
import okhttp3.Response

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * retrofit的网络请求的host动态拦截修改的拦截器
 */
class HostInterceptor(private val dynamicUrl: () -> String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val oldHost = originRequest.url.host//release.course.api.cniao5.com 不带scheme的
        val oldUrlStr = originRequest.url.toString()
        //调试使用
        val newHost = dynamicUrl.invoke().toUri().host ?: oldHost
        val newUrlStr = if (newHost == oldHost) {
            oldUrlStr
        } else oldUrlStr.replace(oldHost, newHost)

        val newBuilder = originRequest.newBuilder()
//        LogUtils.i("Host 拦截器 ${originRequest.url} 拦截后 $newUrlStr")
        newBuilder.url(newUrlStr)
        return chain.proceed(newBuilder.build())
    }

}