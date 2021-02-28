package org.zhiwei.libcore.net

import retrofit2.Call
import retrofit2.await
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 */


/**
 * 密封类，用于接收接口的数据返回封装entity
 */
sealed class DataResult<out R> {

    //成功状态的时候
    data class Success<out T>(val data: T) : DataResult<T>()

    //错误，失败状态的时候
    data class Error(val exception: Exception) : DataResult<Nothing>()

    //加载数据中
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "接口响应: Success[data=$data]"
            is Error -> "接口响应: Error[exception=$exception]"
            Loading -> "接口响应: Loading..."
        }
    }

    /**
     *  返回结果如果是Success类，且data非null，才认为是成功的。
     */
    val DataResult<*>.succeeded
        get() = this is Success && data != null

}


/**
 * 扩展用于处理网络返回数据结果 网络接口请求成功，但是业务成功与否，另一说
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onSuccess(
    action: R.() -> Unit
): DataResult<R> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (succeeded) action.invoke((this as DataResult.Success<R>).data)
    return this
}

/**
 * 这是网络请求出现错误的时候，的回调
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onFailure(
    action: (exception: Throwable) -> Unit
): DataResult<R> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is DataResult.Error) action.invoke(exception)
    return this
}


/**
 * 扩展retrofit的返回数据，调用await，并catch超时等异常
 * @return DataResult 返回格式为ApiResponse封装
 */
suspend fun <T : Any> Call<T>.serverData(): DataResult<T> {
    var result: DataResult<T> = DataResult.Loading
    kotlin.runCatching {
        this.await()
    }.onFailure {
        result = DataResult.Error(RuntimeException(it))
        it.printStackTrace()
    }.onSuccess {
        result = DataResult.Success(it)
    }
    return result
}
