package com.millet.mylibrary.base.ui.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.millet.mylibrary.R


/**
 * @author: Albert Li
 * @contact: albertlii@163.com
 * @time: 2020/7/14 5:09 PM
 * @description: DialogFragment的基类
 * @since: 1.0.0
 */
abstract class BaseDialogFragment : DialogFragment() {
    protected lateinit var rootView: View

    private var dimAmount: Float = 0.5f // 背景默认透明度
    private var widthPer: Float = 0f // 宽度屏幕占比
    private var heightPer: Float = 0f // 高度屏幕占比
    private var gravity: Int = Gravity.CENTER // 位置
    private var animRes: Int = -1 // 动画
    private var alpha: Float = 1f // 控件透明度

    override fun onStart() {
        // 在super之前执行，因为super.onStart()中dialog会执行自己的show方法
        refreshAttributes()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            if (getDialogStyle() != null) getDialogStyle()!! else STYLE_NO_TITLE,
            if (getDialogTheme() != null) getDialogTheme()!! else R.style.DialogNoTitleStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view, savedInstanceState)
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
     * 设置控件背景透明度
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
    private fun refreshAttributes() {
        dialog?.window?.let {
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
            it.attributes = params
            // 设置宽高
            var widthParams = ViewGroup.LayoutParams.WRAP_CONTENT
            var heightParams = ViewGroup.LayoutParams.WRAP_CONTENT
            val dm = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
            if (widthPer > 0) {
                widthParams = (dm.widthPixels * widthPer).toInt()
            }
            if (heightPer > 0) {
                heightParams = (dm.heightPixels * heightPer).toInt()
            }
            it.setLayout(widthParams, heightParams)
        }
    }

    open fun getDialogStyle(): Int? = null

    open fun getDialogTheme(): Int? = null

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initialize(view: View, savedInstanceState: Bundle?)

    open fun isShow(): Boolean {
        return dialog != null && dialog!!.isShowing
    }

    fun show(activity: FragmentActivity) {
        show(activity.supportFragmentManager)
    }

    fun show(fragment: Fragment) {
        show(fragment.requireFragmentManager())
    }

    open fun show(manager: FragmentManager) {
        if (!isShow()) {
            show(manager, this::javaClass.name)
        }
    }

    fun close() {
        if (isShow()) {
            dismiss()
        }
    }

    fun closeAsl() {
        if (isShow()) {
            dismissAllowingStateLoss()
        }
    }
}