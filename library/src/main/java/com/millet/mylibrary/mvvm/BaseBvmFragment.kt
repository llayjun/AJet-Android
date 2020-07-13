package com.millet.mylibrary.mvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.bean.DialogBean
import com.millet.mylibrary.mvvm.vm.BaseViewModel

abstract class BaseBvmFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseBindingFragment<DB>() {

    lateinit var mViewModel: VM

    override fun initDataBinding(inflater: LayoutInflater?, layoutId: Int, container: ViewGroup?): DB {
        val db = super.initDataBinding(inflater, layoutId, container)
        mViewModel = initViewModel()
        lifecycle.addObserver(mViewModel)
        initObserve()
        return db
    }

    /**
     * 将initVieModel暴露出去，方便子类自己判断共享ViewModel
     */
    private fun initViewModel(): VM {
        return ViewModelProvider(viewModelScope()).get(createViewModel()::class.java)
    }

    /**
     * 返回viewModel的实例
     */
    protected abstract fun createViewModel(): VM

    /**
     * viewModel的作用域，一般用于Fragment和activity之间共享时
     */
    protected abstract fun viewModelScope(): ViewModelStoreOwner

    private fun initObserve() {
        // 监听当前ViewModel中 showDialog和error的值
        mViewModel.getShowDialog(this, Observer<DialogBean> {
            if (it.isShow) {
                showDialog()
            } else {
                dismissDialog()
            }
        })
        // 错误
        mViewModel.getThrowable(this, Observer {
            showThrowable(it)
        })
        // 提示
        mViewModel.getToast(this, Observer {
            ToastUtils.showShort(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }

}