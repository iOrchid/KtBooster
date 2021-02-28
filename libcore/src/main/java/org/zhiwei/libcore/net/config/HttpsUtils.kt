package org.zhiwei.libcore.net.config

import android.content.Context
import okhttp3.OkHttpClient
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

/*
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年08月26日 23:34
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 */
object HttpsUtils {

    /**
     * 信任所有证书,会有https请求的安全风险(中间人监听，劫持)
     */
    fun setTrustAllCertificate(okHttpClientBuilder: OkHttpClient.Builder) {
        try {
            val sc = SSLContext.getInstance("TLS")
            val trustAllManager: X509TrustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOfNulls<X509Certificate>(0)
                }
            }
            sc.init(null, arrayOf<TrustManager>(trustAllManager), SecureRandom())
            okHttpClientBuilder.sslSocketFactory(sc.socketFactory, trustAllManager)
            //如果需要兼容安卓5.0以下，可以使用这句
            //okHttpClientBuilder.sslSocketFactory(new TLSSocketFactory(), trustAllManager);
            okHttpClientBuilder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 只信任指定证书（传入字符串）
     */
    fun setCertificate(
        context: Context?,
        okHttpClientBuilder: OkHttpClient.Builder,
        cerStr: String
    ) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val byteArrayInputStream = ByteArrayInputStream(cerStr.toByteArray())
            val ca: Certificate = certificateFactory.generateCertificate(byteArrayInputStream)
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
            byteArrayInputStream.close()
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(keyStore)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, SecureRandom())
            okHttpClientBuilder.sslSocketFactory(
                sslContext.socketFactory,
                (tmf.trustManagers[0] as X509TrustManager)
            )
            okHttpClientBuilder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 只信任指定证书（传入raw资源ID,或者可以用assets类似）
     */
    fun setCertificate(context: Context, okHttpClientBuilder: OkHttpClient.Builder, cerResID: Int) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            //可以换成其他形式的 cert证书获取方式，如assets，
            val inputStream: InputStream = context.resources.openRawResource(cerResID)
            val ca: Certificate = certificateFactory.generateCertificate(inputStream)
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
            inputStream.close()
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(keyStore)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, SecureRandom())
            okHttpClientBuilder.sslSocketFactory(
                sslContext.socketFactory,
                (tmf.trustManagers[0] as X509TrustManager)
            )
            okHttpClientBuilder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 批量信任证书
     */
    fun setCertificates(
        context: Context,
        okHttpClientBuilder: OkHttpClient.Builder,
        vararg cerResIDs: Int
    ) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            for (i in cerResIDs.indices) {
                val ca: Certificate = certificateFactory.generateCertificate(
                    context.resources.openRawResource(
                        cerResIDs[i]
                    )
                )
                keyStore.setCertificateEntry("ca$i", ca)
            }
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(keyStore)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, SecureRandom())
            okHttpClientBuilder.sslSocketFactory(
                sslContext.socketFactory,
                (tmf.trustManagers[0] as X509TrustManager)
            )
            okHttpClientBuilder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}