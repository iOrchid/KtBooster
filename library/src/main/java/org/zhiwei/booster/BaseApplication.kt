package org.zhiwei.booster

import android.app.Application
import org.zhiwei.booster.core.kv.KvTools
import org.zhiwei.booster.core.ui.application
import org.zhiwei.booster.dokit.AssistantApp

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 抽象基类Application
 */
abstract class BaseApplication : Application() {

    //标记是否需要显示调试工具
    protected abstract val showKit: Boolean

    // 配置用于项目的baseHost，用于DoKit的配置切换
    protected open val baseHost: Map<String, String> = emptyMap()

    override fun onCreate() {
        super.onCreate()
        KvTools.initKvTools(application)
        if (showKit) {
            AssistantApp.configKit(application, baseHost) { observeHostChange(it) }
        }
    }

    open fun observeHostChange(url: String?) {
        //有需要感知项目baseUrl的变化的，在继承的Application中覆写此函数，来处理
    }
}