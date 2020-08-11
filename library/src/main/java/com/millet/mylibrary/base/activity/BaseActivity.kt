package com.millet.mylibrary.base.activity

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AdaptScreenUtils

/**
 * @author  zyl
 * @date  2020/7/8 2:08 PM
 */
abstract class BaseActivity : AppCompatActivity() {

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initWindow(savedInstanceState)
        setContentView(getLayoutId())
        initView(savedInstanceState)
        loadData(savedInstanceState)
    }

    /**
     * 作为window操作
     */
    open fun initWindow(savedInstanceState: Bundle?) {}

    /**
     * 返回布局界面
     */
    abstract fun getLayoutId(): Int



    /**
     * 初始化视图
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 加载数据
     */
    protected abstract fun loadData(savedInstanceState: Bundle?)

    /**
     * 今日头条适配方式
     *
     * @return
     */
    override fun getResources(): Resources? {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }

}