package org.zhiwei.booster.dokit

import android.app.Application
import com.didichuxing.doraemonkit.DoKit
import com.didichuxing.doraemonkit.kit.AbstractKit
import org.zhiwei.booster.core.ui.StringParamUnit

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年12月30日 18:24
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 调试相关的application配置  放在application中初始化
 */
internal object AssistantApp {

    /**
     * 配置工具
     */
    fun configKit(
        application: Application,
        mapHost: Map<String, String>,
        hostCallback: StringParamUnit
    ) {
        val kits = arrayListOf<AbstractKit>(ServerKit(mapHost, hostCallback))
        DoKit.Builder(application)
            .productId("")
            .customKits(kits)
            .build()
    }
}