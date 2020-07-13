package com.millet.mylibrary.mvvm

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.bean.DialogBean
import com.millet.mylibrary.mvvm.vm.BaseViewModel

abstract class BaseBvmActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseBindingActivity<DB>() {

    lateinit var mViewModel: VM

    override fun initDataBinding(layoutId: Int): DB {
        val db = super.initDataBinding(layoutId)
        mViewModel = initViewModel()
        lifecycle.addObserver(mViewModel)
        initObserve()
        return db
    }

    /**
     * 将initVieModel暴露出去，方便子类自己判断共享ViewModel
     */
    private fun initViewModel(): VM {
        return ViewModelProvider(this).get(createViewModel()::class.java)
    }

    protected abstract fun createViewModel(): VM

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
        // 关闭当前页面
        mViewModel.getFinish(this, Observer {
            finish()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }

}