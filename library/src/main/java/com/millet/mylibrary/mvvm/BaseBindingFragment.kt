package com.millet.mylibrary.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.R
import com.millet.mylibrary.ui.dialog.LoadingDialog
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException

abstract class BaseBindingFragment<DB : ViewDataBinding> : Fragment() {

    lateinit var mDataBinding: DB

    lateinit var mContext: Context

    lateinit var mActivity: FragmentActivity

    private lateinit var mLoadingDialog: LoadingDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDataBinding = initDataBinding(inflater, getLayoutId(), container)
        mDataBinding.lifecycleOwner = this
        return mDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = requireActivity()
        initView(savedInstanceState)
        loadData(savedInstanceState)
    }

    /**
     * 返回布局界面
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化DataBinding
     */
    protected open fun initDataBinding(inflater: LayoutInflater?, @LayoutRes layoutId: Int, container: ViewGroup?): DB {
        return DataBindingUtil.inflate(inflater!!, layoutId, container, false)
    }

    /**
     * 初始化视图
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 加载数据
     */
    protected abstract fun loadData(savedInstanceState: Bundle?)

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
    fun showThrowable(throwable: Throwable?) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort(R.string.result_network_error)
            return
        }
        return when (throwable) {
            is ConnectException -> {
                ToastUtils.showShort(R.string.result_server_error)
            }
            is SocketTimeoutException -> {
                ToastUtils.showShort(R.string.result_server_timeout)
            }
            is JSONException -> {
                ToastUtils.showShort(R.string.result_json_error)
            }
            else -> {
                ToastUtils.showShort(R.string.result_empty_error)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBinding.unbind()
    }

}