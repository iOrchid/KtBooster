package org.zhiwei.booster.core.net.config

import java.io.IOException
import java.io.InputStream
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*


/*
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
 *  用于设置okHttp的https支持的工具类
 */
object HttpsTools {

    //对指定证书的信任
    fun getSslSocketFactory(
        certificates: Array<InputStream>,
        bksFile: InputStream?,
        password: String?
    ): SSLSocketFactory? {
        return try {
            val trustManagers: Array<TrustManager>? = prepareTrustManager(*certificates)
            val keyManagers: Array<KeyManager>? = prepareKeyManager(bksFile, password)
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(
                keyManagers,
                arrayOf<TrustManager>(MyTrustManager(chooseTrustManager(trustManagers))),
                SecureRandom()
            )
            sslContext.socketFactory
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        } catch (e: KeyManagementException) {
            throw AssertionError(e)
        } catch (e: KeyStoreException) {
            throw AssertionError(e)
        }
    }

    private fun prepareTrustManager(vararg certificates: InputStream): Array<TrustManager>? {
        if (certificates.isEmpty()) return null
        try {
            val certificateFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            for ((index, certificate) in certificates.withIndex()) {
                val certificateAlias = (index).toString()
                keyStore.setCertificateEntry(
                    certificateAlias,
                    certificateFactory.generateCertificate(certificate)
                )
                try {
                    certificate?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            val trustManagerFactory: TrustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            return trustManagerFactory.trustManagers
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun prepareKeyManager(
        bksFile: InputStream?,
        password: String?
    ): Array<KeyManager>? {
        try {
            if (bksFile == null || password == null) return null
            val clientKeyStore: KeyStore = KeyStore.getInstance("BKS")
            clientKeyStore.load(bksFile, password.toCharArray())
            val keyManagerFactory: KeyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManagerFactory.init(clientKeyStore, password.toCharArray())
            return keyManagerFactory.keyManagers
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnrecoverableKeyException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun chooseTrustManager(trustManagers: Array<TrustManager>?): X509TrustManager? {
        for (trustManager in trustManagers!!) {
            if (trustManager is X509TrustManager) {
                return trustManager
            }
        }
        return null
    }

    class MyTrustManager(localTrustManager: X509TrustManager?) :
        X509TrustManager {
        private val defaultTrustManager: X509TrustManager?
        private val localTrustManager: X509TrustManager?

        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<X509Certificate?>?,
            authType: String?
        ) {
            //client check
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<X509Certificate?>?,
            authType: String?
        ) {
            try {
                defaultTrustManager?.checkServerTrusted(chain, authType)
            } catch (ce: CertificateException) {
                localTrustManager?.checkServerTrusted(chain, authType)
            }
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

        init {
            val var4: TrustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            var4.init(null as KeyStore?)
            defaultTrustManager = chooseTrustManager(var4.trustManagers)
            this.localTrustManager = localTrustManager
        }
    }

    /**
     * 对所有站点的信任
     *
     * @return SSLSocketFactory工厂对象
     */
    fun initSSLSocketFactory(): SSLSocketFactory? {
        //创建加密上下文
        var sslContext: SSLContext? = null
        try {
            //这里要与服务器的算法类型保持一致TSL/SSL
            sslContext = SSLContext.getInstance("SSL")
            val xTrustArray: Array<X509TrustManager> =
                arrayOf<X509TrustManager>(initTrustManager())
            sslContext.init(
                null,
                xTrustArray, SecureRandom()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sslContext?.socketFactory
    }

    /**
     * 生成TrustManager信任管理器类
     *
     * @return X509TrustManager
     */
    fun initTrustManager(): X509TrustManager {
        return object : X509TrustManager {

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?
            ) {
                //server check
            }

            override fun getAcceptedIssuers() = arrayOf<X509Certificate?>()

            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?
            ) {
                //client check
            }
        }
    }
}