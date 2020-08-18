package org.zhiwei.libnet

import android.net.Uri
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.collection.SimpleArrayMap
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.zhiwei.libnet.config.KtHttpLogInterceptor
import org.zhiwei.libnet.config.RetryInterceptor
import org.zhiwei.libnet.support.IHttpCallback
import java.io.File
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
 * todo 1、管理状态，取消；2、完善封装特性；
 */
object HttpApi {

	private const val TAG = "HttpApi"

	//存储请求，用于取消
	private val callMap = SimpleArrayMap<Any, Call>()


	//gson对象，免得每次都创建
	private val gson = Gson()

	//url的配置 以/结尾，而path就不用/开始了
	private var baseUrl: String? = null

	var maxRetry = 0//最大重试 次数

	//okHttpClient对象构建配置
	private var defaultClient = OkHttpClient.Builder()
			.callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
			.connectTimeout(10, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
			.readTimeout(10, TimeUnit.SECONDS)//读取服务器返回数据的时长
			.writeTimeout(10, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
			.retryOnConnectionFailure(true)//重连
			.followRedirects(false)//重定向
			.cache(Cache(File("sdcard/cache", "okhttpCache"), 1024))//http 的缓存大小，位置
			.cookieJar(CookieJar.NO_COOKIES)
//        .cookieJar(LocalCookieJar())
			.addNetworkInterceptor(KtHttpLogInterceptor {
				logLevel(KtHttpLogInterceptor.LogLevel.BODY)
			})//添加网络拦截器，可以对okHttp的网络请求做拦截处理，不同于应用拦截器，这里能感知所有网络状态，比如重定向。
			.addNetworkInterceptor(RetryInterceptor(maxRetry))
//        .hostnameVerifier()//主机host的校验
//        .sslSocketFactory(HttpsTools.initSSLSocketFactory(),HttpsTools.initTrustManager())
			.build()

	//可公开的okHttpClient
	var okClient = defaultClient


	/**
	 * 配置server的根url地址,也可以自定义okClient
	 * [baseUrl] 项目接口的baseUrl
	 * [client] 函数参数，创建okHttpClient对象
	 */
	fun initConfig(
			@NonNull baseUrl: String,
			client: () -> OkHttpClient = { defaultClient }
	): HttpApi {
		this.baseUrl = baseUrl
		this.defaultClient = client.invoke()
		return this
	}

	//region get请求

	/**
	 * get请求服务器数据,同步请求请求，
	 * [data]为map形式的key -- value 请求参数，或者别的形式
	 * 可使用getSync(){} 也可以 直接得到返回结果，再配合toEntity转化为具体的data class
	 */
	fun getSync(
			data: Map<String, String>? = null,
			@NonNull path: String,
			block: (Response?) -> Unit
	): Response? {
		val request = buildGetRequest(path, data)
		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		val rsp = newCall.execute()
		block.invoke(rsp)
		return rsp
	}


	/**
	 * get请求服务器数据,异步请求，接口回调形式
	 * [data]为map形式的key -- value 请求参数，或者别的形式
	 */
	fun get(data: Map<String, String>? = null, @NonNull path: String, callback: IHttpCallback) {
		val request = buildGetRequest(path, data)
		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		newCall.enqueue(callback(callback))
	}

	/**
	 * get请求服务器数据,异步请求，liveData回调形式
	 * [data]为map形式的key -- value 请求参数，或者别的形式
	 */
	fun get(
			data: Map<String, String>? = null,
			@NonNull path: String,
			liveData: MutableLiveData<String?>
	) {
		val request = buildGetRequest(path, data)
		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		newCall.enqueue(liveBack(liveData))
	}

	/**
	 * get请求服务器数据,异步请求，lambda回调形式
	 * [data]为map形式的key -- value 请求参数，或者别的形式
	 */
	fun get(data: Map<String, String>? = null, @NonNull path: String, method: (String?) -> Unit) {
		val request = buildGetRequest(path, data)
		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		newCall.enqueue(lambdaInvoke(method))
	}

	/**
	 * 构建get请求request
	 * [params]用于拼接url中的请求key--value，可以没有 也是作为本次请求的id tag
	 * [path]请求地址path 用于和baseUrl拼接成完整请求url
	 */
	private fun buildGetRequest(
			@NonNull path: String,
			@Nullable params: Map<String, String>? = null
	): Request {
		checkBaseUrl()
		//便于对不是baseUrl的链接做接受;下面的处理方式可能不是最佳，也可以方法签名中默认参数flag标记是否独立url
		val url = if (path.startsWith(
						"http://",
						true
				) || path.startsWith("Https://")
		) path else "$baseUrl$path"
		//有get请求参数
//        if (!params.isNullOrEmpty()) {
//            val sb = StringBuilder()
//            with(sb) {
//                append("$url?")
//                params.forEach { item ->
//                    append("${item.key}=${item.value}&")
//                }
//            }
//            url = sb.toString().dropLast(1)//上面的拼接，会在最后多一个&符号，所去掉
//        }
		//使用合理的拼接参数的方式，而不是sb的方式
		val urlBuilder = url.toHttpUrl().newBuilder()
		params?.forEach { item ->
			urlBuilder.addEncodedQueryParameter(item.key, item.value.toString())
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
	 * post请求服务器数据，同步操作
	 * 可使用postSync(){} 也可以 直接得到返回结果，再配合toEntity转化为具体的data class
	 */
	fun postSync(any: Any? = null, @NonNull path: String, block: (Response?) -> Unit): Response? {
		val request = buildJsonPost(any, path)
		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		val rsp = newCall.execute()
		block.invoke(rsp)
		return rsp
	}

	/**
	 * post请求服务器数据，异步请求
	 * 普通的接口回调
	 */
	fun post(any: Any? = null, @NonNull path: String, callback: IHttpCallback) {
		val request = buildJsonPost(any, path)
		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		newCall.enqueue(callback(callback))
	}


	/**
	 * post请求服务器数据，异步请求
	 * 使用lambda形式的函数回调
	 */
	fun post(any: Any? = null, @NonNull path: String, method: (String?) -> Unit) {
		val request = buildJsonPost(any, path)

		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		newCall.enqueue(lambdaInvoke(method))
	}

	/**
	 * post请求服务器数据，异步请求
	 * 使用liveData作为回调
	 */
	fun post(any: Any? = null, @NonNull path: String, liveData: MutableLiveData<String?>) {
		val request = buildJsonPost(any, path)
		val newCall = okClient.newCall(request)
		//记录请求，用于取消
		callMap.put(request.tag(), newCall)
		newCall.enqueue(liveBack(liveData))
	}


	/**
	 * 构建post的请求request
	 * [path]请求的service
	 * [any] 请求数据对象 也是作为本次请求的id tag
	 */
	private fun buildJsonPost(any: Any?, @NonNull path: String): Request {
		checkBaseUrl()
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
				.get()//默认即get请求，也就是method（GET）
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
				kotlin.runCatching {
					response.body?.string()
				}.onSuccess { str ->
					callback(str)
					Log.i(TAG, "ResponseBodyString: $str")
				}.onFailure {
					it.printStackTrace()
					Log.e(TAG, "ResponseBodyString Error")
				}

			}
		}
	}

	/**
	 * 校验检查baseUrl
	 */
	private fun checkBaseUrl() {
		//如果baseUrl没有配置，则抛异常，提示,后期可以更严格的正则匹配url为合法网址
		baseUrl ?: throw IllegalArgumentException("BaseUrl必须要初始化配置正确，方可正常使用HttpApi")
		Uri.parse(baseUrl).scheme
				?: throw IllegalArgumentException("BaseUrl格式不合法,请检查是否有scheme")
	}

	/**
	 * 取消网络请求，tag就是每次请求的id 标记，也就是请求的传参
	 */
	fun cancel(tag: Any) {
		callMap.get(tag)?.cancel()
	}

	/**
	 * 取消所有网络请求
	 */
	fun cancelAll() {
		for (i in 0 until callMap.size()) {
			callMap.get(callMap.keyAt(i))?.cancel()
		}
	}
}