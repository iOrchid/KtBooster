package org.zhiwei.booster

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月29日 11:55
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Fragment基类封装，包含必要的公用属性、扩展函数
 */
abstract class KtFragment : Fragment {

    /**
     * 无参构造函数
     */
    constructor() : super()

    /**
     * 可以填入layout布局的构造函数，使用viewBinding的方便
     * [layout] layout布局文件的id
     */
    constructor(@LayoutRes layout: Int) : super(layout)


    /**
     * 扩展用于liveData便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     */
    protected fun <T : Any> LiveData<T>.observeKt(block: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer {
            block(it)
        })
    }
}