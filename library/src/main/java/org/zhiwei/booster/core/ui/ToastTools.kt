package org.zhiwei.booster.core.ui

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


/*
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 封装的便捷显示toast的方法
 */
object ToastTools {

    fun showShort(
        context: Context,
        msg: String?,
        gravity: Int? = null,
        xOffset: Int = 0,
        yOffset: Int = 0
    ) {
        Toast.makeText(context, "$msg", Toast.LENGTH_SHORT).apply {
            gravity?.let {
                setGravity(gravity, xOffset, yOffset)
            }
        }.show()

    }

    fun showLong(
        context: Context, msg: String?,
        gravity: Int? = null,
        xOffset: Int = 0,
        yOffset: Int = 0
    ) {
        Toast.makeText(context, "$msg", Toast.LENGTH_LONG).apply {
            gravity?.let {
                setGravity(gravity, xOffset, yOffset)
            }
        }.show()
    }

    fun Activity.showToast(
        msg: String?,
        gravity: Int? = null,
        xOffset: Int = 0,
        yOffset: Int = 0
    ) {
        Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).apply {
            gravity?.let {
                setGravity(gravity, xOffset, yOffset)
            }
        }.show()
    }

    fun Activity.showLongToast(
        msg: String?,
        gravity: Int? = null,
        xOffset: Int = 0,
        yOffset: Int = 0
    ) {

        Toast.makeText(this, "$msg", Toast.LENGTH_LONG).apply {
            gravity?.let {
                setGravity(gravity, xOffset, yOffset)
            }
        }.show()
    }

    fun Fragment.showToast(
        msg: String?,
        gravity: Int? = null,
        xOffset: Int = 0,
        yOffset: Int = 0
    ) {
        Toast.makeText(requireContext(), "$msg", Toast.LENGTH_SHORT).apply {
            gravity?.let {
                setGravity(gravity, xOffset, yOffset)
            }
        }.show()
    }

    fun Fragment.showLongToast(
        msg: String?,
        gravity: Int? = null,
        xOffset: Int = 0,
        yOffset: Int = 0
    ) {
        Toast.makeText(requireContext(), "$msg", Toast.LENGTH_LONG).apply {
            gravity?.let {
                setGravity(gravity, xOffset, yOffset)
            }
        }.show()
    }

}