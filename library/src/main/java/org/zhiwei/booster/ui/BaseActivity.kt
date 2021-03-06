package org.zhiwei.booster.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import org.zhiwei.booster.core.ui.bindView
import org.zhiwei.booster.core.ui.dismissKeyBoard
import org.zhiwei.booster.core.ui.viewLifecycleOwner

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 抽象的Activity的基类
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity {

    /**
     * 无参构造函数
     */
    constructor() : super()

    /**
     * 可以填入layout布局的构造函数，使用viewBinding的方便
     * [layout] layout布局文件的id
     */
    constructor(@LayoutRes layout: Int) : super(layout)

    private lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindView<VB>(getLayoutRes()).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.initView()
        }
        initConfig()
        initData()
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    /**
     * 必要的view初始化
     */
    protected open fun VB.initView() {
        //
    }


    /**
     * 必要的配置初始化
     */
    protected open fun initConfig() {
        //
    }

    /**
     * 必要的数据初始化
     */
    protected open fun initData() {
        //
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (currentFocus is EditText) {
            //获取聚焦的控件的坐标
            val vLocation = IntArray(2)
            currentFocus?.getLocationOnScreen(vLocation)
            //获取聚焦的控件的四点坐标
            val left = vLocation[0].toFloat()
            val top = vLocation[1].toFloat()
            val right = left + (currentFocus?.width ?: 0)
            val bottom = top + (currentFocus?.height ?: 0)
            //判断触摸点坐标是否在聚焦的控件内
            event?.let {
                if (!(event.rawX in left..right && event.rawY in top..bottom)) {
                    //不在聚焦坐标内的点击，则关闭键盘
                    currentFocus?.let {
                        dismissKeyBoard(it)
                        //点击editText之外的地方，来回调其他需要响应
                        touchEtOutside(it)
                        (supportFragmentManager.primaryNavigationFragment as? BaseFragment)?.touchEtOutside(
                            it
                        )
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 点击了EditText之外的地方，需要响应该事件的可以重写该函数
     */
    protected open fun touchEtOutside(view: View) {}

    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

}