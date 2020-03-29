package org.zhiwei.booster

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月29日 11:52
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 封装的Activity的基类，包含一些必要的公用属性或扩展函数
 */
abstract class BaseActivity:AppCompatActivity() {



    /**
     * 扩展用于liveData便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     */
    protected fun <T : Any> LiveData<T>.observeKt(block: (T) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            block(it)
        })
    }
}