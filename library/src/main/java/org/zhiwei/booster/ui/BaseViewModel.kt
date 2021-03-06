package org.zhiwei.booster.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.zhiwei.booster.BaseApplication

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 封装的抽象viewModel的基类
 */
abstract class BaseViewModel(protected val application: BaseApplication? = null) : ViewModel() {

    private val jobs = mutableListOf<Job>()
    val isLoading = MutableLiveData<Boolean>()//标记网络loading状态

    /**
     * 协程 网络请求
     */
    protected fun serverAwait(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        isLoading.value = true
        block.invoke(this)
        isLoading.value = false
    }.addTo(jobs)


    override fun onCleared() {
        jobs.forEach { it.cancel() }
        super.onCleared()
    }


    /**
     * 扩展函数，用于viewModel中的job 添加到list方便
     */
    private fun Job.addTo(list: MutableList<Job>) {
        list.add(this)
    }
}
