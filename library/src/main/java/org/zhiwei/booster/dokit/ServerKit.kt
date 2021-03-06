package org.zhiwei.booster.dokit

import android.content.Context
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.SingleChoiceListener
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.didichuxing.doraemonkit.kit.AbstractKit
import org.zhiwei.booster.R
import org.zhiwei.booster.core.kv.KvTools
import org.zhiwei.booster.core.ui.StringParamUnit

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年12月30日 18:24
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 配置请求接口的host，包含qa 线上 和自定义的local host，可做成选择ip和host、port的方式
 */
internal class ServerKit(
    mapHost: Map<String, String>,
    private val hostCallback: StringParamUnit? = null
) : AbstractKit() {

    private val _mapHost = mutableMapOf<String, String>()
    private var hostTitles: List<String>
    private var hostValues: List<String>

    init {
        _mapHost.putAll(mapHost)
        _mapHost["研发Native接口"] = ""
        hostTitles = _mapHost.keys.toList()
        hostValues = _mapHost.values.toList()
    }

    /**
     * icon点击事件，这里的context是application，不适合dialog使用
     */
    override fun onClick(context: Context?) {
        if (context == null) return

        //选中的host
        var checkHost = KvTools.getString(CONFIG_KEY_SERVER_HOST, hostValues.first())
        var checkedIndex = hostValues.indexOf(checkHost)
        if (checkedIndex < 0) checkedIndex = hostTitles.size - 1//也就是研发Native那个

        //启用输入的host
        MaterialDialog(context)
            .title(R.string.str_aide_title_server_host)
            .cancelable(false)
            .cancelOnTouchOutside(false)
            .listItemsSingleChoice(
                items = hostTitles,
                initialSelection = checkedIndex,
                waitForPositiveButton = false,//不等待positiveButton的点击事件，就调用callback
                selection = object : SingleChoiceListener {
                    override fun invoke(dialog: MaterialDialog, index: Int, text: CharSequence) {
                        //最后一个index是native输入框的内容，再下面的input的invoke中保存了
                        checkHost = hostValues[index]
                        //输入内容的变动显示
                        dialog.getCustomView().findViewById<EditText>(R.id.md_input_message).apply {
                            setText(checkHost)
                            setSelection(0)
                        }
                    }
                })
            .input(
                hint = "请输入项目接口的BaseURL",
                prefill = checkHost,
                waitForPositiveButton = false,//不等待positiveButton的点击事件，就调用callback
                callback = object : InputCallback {
                    override fun invoke(dialog: MaterialDialog, sequence: CharSequence) {
                        var inputStr = sequence.toString()
                        if (!inputStr.startsWith("http")) {
                            inputStr = "http://$inputStr"
                        }
                        //针对retrofit需要baseUrl必须/结尾
                        if (!inputStr.endsWith('/')) {
                            inputStr = "$inputStr/"
                        }
                        checkHost = inputStr
                    }
                })
            .positiveButton(text = "确定") {
                KvTools.put(CONFIG_KEY_SERVER_HOST, checkHost)
                //回调
                hostCallback?.invoke(checkHost)
            }
            .negativeButton(text = "取消")
            .onShow { dialog ->
                dialog.getCustomView().findViewById<EditText>(R.id.md_input_message).apply {
                    //避免聚焦弹键盘
                    isFocusable = false
                    isFocusableInTouchMode = false
                    setSelection(0)
                    clearFocus()
                }
            }.show()

    }


    override val icon: Int
        get() = R.drawable.icon_server_host
    override val name: Int
        get() = R.string.str_aide_title_server_host

    override fun onAppInit(context: Context?) {
        //do something needed
    }
}


//用于标记host配置的key
const val CONFIG_KEY_SERVER_HOST = "server_host"