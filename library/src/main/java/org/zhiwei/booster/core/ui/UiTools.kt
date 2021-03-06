package org.zhiwei.booster.core.ui

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月18日 12:09
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 和UI相关的工具类，扩展函数、属性
 */
object UiTools {

    val screenDensity = Resources.getSystem().displayMetrics.density//屏幕密度
    val screenDensityDpi = Resources.getSystem().displayMetrics.densityDpi//屏幕密度 dpi
    val screenScaleDensity = Resources.getSystem().displayMetrics.scaledDensity//缩放密度
    val screenHpx = Resources.getSystem().displayMetrics.heightPixels//高像素
    val screenWpx = Resources.getSystem().displayMetrics.widthPixels//宽 像素
    val screenWdp = Resources.getSystem().displayMetrics.xdpi//宽 dp
    val screenHdp = Resources.getSystem().displayMetrics.ydpi//高 dp

    //region 简单转换

    /**
     * dp转为px单位
     */
    fun dp2px(dp: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    /**
     * px转为dp单位
     */
    fun px2dp(px: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    /**
     * 字号单位sp 转px
     */
    fun sp2px(sp: Float) = dp2px(sp)

    /**
     * px 转 字号单位sp
     */
    fun px2sp(px: Float) = px2dp(px)

    //endregion

    //region 基本宽高获取

    /**
     * 获取屏幕的宽度
     */
    fun getScreenWidth(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.x
    }

    /**
     * 获取屏幕的高度
     */
    fun getScreenHeight(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.y
    }

    /**
     * 测量View宽度
     */
    fun measureWidth(view: View): Int {
        return measureView(view)?.get(0) ?: -1
    }

    /**
     * 测量View高度
     */
    fun measureHeight(view: View): Int {
        return measureView(view)?.get(1) ?: -1
    }

    /**
     * 测量View的宽高，返回Int数组，width height
     */
    fun measureView(view: View): IntArray? {
        var lp: ViewGroup.LayoutParams? = view.layoutParams
        if (lp == null) {
            lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
        val lpHeight = lp.height
        val heightSpec: Int
        heightSpec = if (lpHeight > 0) {
            View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        view.measure(widthSpec, heightSpec)
        return intArrayOf(view.measuredWidth, view.measuredHeight)
    }

    //endregion


}