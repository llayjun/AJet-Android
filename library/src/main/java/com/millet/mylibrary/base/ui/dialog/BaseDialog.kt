package com.millet.mylibrary.base.ui.dialog

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog


/**
 * @author: Albert Li
 * @contact: albertlii@163.com
 * @time: 2020/6/28 2:02 PM
 * @description: dialog的基类
 * @since: 1.0.0
 */
abstract class BaseDialog : AppCompatDialog {

    private var dimAmount: Float = 0.5f // 背景默认透明度
    private var widthPer: Float = 0f // 宽度屏幕占比
    private var heightPer: Float = 0f // 高度屏幕占比
    private var gravity: Int = Gravity.CENTER // 位置
    private var animRes: Int = -1 // 动画
    private var alpha: Float = 1f // 控件透明度

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initialize(savedInstanceState)
        refreshAttributes()
    }

    protected open fun initContentView() {
        setContentView(getLayoutId())
    }

    /**
     * 设置宽度
     */
    fun setWidthPer(widthPer: Float) {
        this.widthPer = widthPer
    }

    /**
     * 设置高度
     */
    fun setHeightPer(heightPer: Float) {
        this.heightPer = heightPer
    }

    /**
     * 设置位置
     */
    fun setGravity(gravity: Int) {
        this.gravity = gravity
    }

    /**
     * 设置窗口透明度
     */
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float) {
        this.dimAmount = dimAmount
    }

    /**
     * 设置背景透明度
     */
    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        this.alpha = alpha
    }


    /**
     * 设置显示和隐藏动画
     */
    fun setAnimationRes(animation: Int) {
        this.animRes = animation
    }

    /**
     * 刷新属性
     */
    fun refreshAttributes() {
        window!!.let {
            var _dimAmount: Float = dimAmount
            if (dimAmount < 0f) {
                _dimAmount = 0f
            }
            if (dimAmount > 1f) {
                _dimAmount = 1f
            }
            val params: WindowManager.LayoutParams = it.attributes
            params.gravity = gravity
            params.windowAnimations = animRes
            params.dimAmount = _dimAmount
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            params.alpha = alpha
            // 设置宽高
            var widthParams = ViewGroup.LayoutParams.WRAP_CONTENT
            var heightParams = ViewGroup.LayoutParams.WRAP_CONTENT
            val point = Point()
            it.windowManager.defaultDisplay.getSize(point)
            if (widthPer > 0) {
                widthParams = (point.x * widthPer).toInt()
            }
            if (heightPer > 0) {
                heightParams = (point.y * heightPer).toInt()
            }
            params.width = widthParams
            params.height = heightParams
            it.attributes = params
        }
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initialize(savedInstanceState: Bundle?)
}