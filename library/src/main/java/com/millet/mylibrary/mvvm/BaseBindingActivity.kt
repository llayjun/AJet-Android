package com.millet.mylibrary.mvvm

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.R
import com.millet.mylibrary.ui.dialog.LoadingDialog
import com.millet.mylibrary.util.ActivityUtil
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException

abstract class BaseBindingActivity<DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mDataBinding: DB

    private lateinit var mContext: Context

    private lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mLoadingDialog = LoadingDialog.create(this, "加载中...", false, null)!!
        initWindow(savedInstanceState)
        initDataBinding(getLayoutId())
        init(savedInstanceState)
        initData(savedInstanceState)
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
     * 初始化DataBinding
     */
    private fun initDataBinding(@LayoutRes layoutId: Int) {
        mDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mDataBinding.lifecycleOwner = this
    }

    /**
     * 初始化viewModel
     */
    protected open fun init(savedInstanceState: Bundle?) {}

    /**
     * 初始化
     */
    protected abstract fun initData(savedInstanceState: Bundle?)

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

    /**
     * 显示用户等待框
     */
    protected open fun showDialog() {
        if (!mLoadingDialog.isShowing)
            mLoadingDialog.show()
    }

    /**
     * 隐藏等待框
     */
    protected open fun dismissDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    /**
     * 网络错误问题
     */
    protected fun showThrowable(throwable: Throwable?) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort(R.string.result_network_error)
            return
        }
        return when (throwable) {
            is ConnectException -> ToastUtils.showShort(R.string.result_server_error)
            is SocketTimeoutException -> ToastUtils.showShort(R.string.result_server_timeout)
            is JSONException -> ToastUtils.showShort(R.string.result_json_error)
            else -> ToastUtils.showShort(R.string.result_empty_error)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBinding.unbind()
    }

}