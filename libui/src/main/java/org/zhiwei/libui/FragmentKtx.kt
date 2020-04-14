package org.zhiwei.libui

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月19日 14:43
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Fragment相关的扩展
 */

//region Fragment相关的扩展

/**
 * 关闭软键盘
 * [v] 事件控件view
 */
fun Fragment.dismissKeyboard(v: View) {
    val imm =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(v.windowToken, 0)
}

/**
 * androidx的Fragment扩展显示Toast的函数
 * [text]文本
 * [duration] Toast.LENGTH_SHORT 或者 Toast。LENGTH_LONG，也就是 0 或 1 默认short
 */
fun Fragment.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), text, duration).show()
}


/**
 * 设置状态栏的颜色
 * [color]颜色值，最好是Color.parse(),或者直接Color.Red等样式
 */
fun Fragment.statusBar(@ColorInt color: Int) {
    //After LOLLIPOP not translucent status bar
    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    //Then call setStatusBarColor.
    requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    val typedValue = TypedValue()
    requireActivity().theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
    requireActivity().window.statusBarColor = color
}

//endregion