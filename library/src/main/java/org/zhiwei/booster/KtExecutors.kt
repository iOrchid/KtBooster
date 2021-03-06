package org.zhiwei.booster

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月28日 10:43
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 线程executors，包含常用的IO、network、Main的线程执行管理器
 */
object KtExecutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()//本地数据执行runner
    val networkIO: Executor = Executors.newFixedThreadPool(3)//网络请求的线程runner

    //用于回调于UI线程的runner
    val mainThreadExecutor: Executor = Executor { runnable ->
        Handler(Looper.getMainLooper()).post(runnable)
    }
}