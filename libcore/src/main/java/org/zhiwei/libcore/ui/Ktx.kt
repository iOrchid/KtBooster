package org.zhiwei.libcore.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.zhiwei.libcore.R

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * app相关的扩展函数
 */

//方便使用application变量
val Application.application
    get() = this


//<editor-folder desc=Activity相关扩展>

//方便使用context变量名称
val Activity.context
    get() = this

val ComponentActivity.viewLifecycleOwner
    get() = this

/**
 * Activity中使用DataBinding时setContentView的简化
 * [layout] 布局文件
 * @return 返回一个Binding的对象实例
 */
fun <T : ViewDataBinding> Activity.bindView(@LayoutRes layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)
}

/**
 * Activity中使用DataBinding时setContentView的简化
 * [layout] 布局文件
 * @return 返回一个Binding的对象实例 T 类型的 可null的
 */
fun <T : ViewDataBinding> Activity.bindView(view: View): T? {
    return DataBindingUtil.bind<T>(view)
}


/**
 * 设置状态栏的颜色
 * [color]颜色值，最好是Color.parse(),或者直接Color.Red等样式
 */
fun Activity.statusBar(@ColorInt color: Int) {
    //After LOLLIPOP not translucent status bar
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    //Then call setStatusBarColor.
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    val typedValue = TypedValue()
    theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
    window.statusBarColor = color
}

/**
 * 界面Activity的沉浸式状态栏，使得可以在状态栏里面显示部分需要的图片
 * 注意点，需要在setContentView之前调用该函数才生效
 */
fun Activity.immediateStatusBar() {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

/**
 * 软键盘的隐藏
 * [view] 事件控件View
 */
fun Activity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}


//</editor-folder>


// <editor-folder desc=Fragment相关扩展>

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
    requireActivity().apply {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //Then call setStatusBarColor.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        window.statusBarColor = color
    }
}


//</editor-folder>