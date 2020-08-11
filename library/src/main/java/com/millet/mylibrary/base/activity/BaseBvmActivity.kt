package com.millet.mylibrary.base.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.bean.DialogBean
import com.millet.mylibrary.base.vm.BaseViewModel

abstract class BaseBvmActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseBindingActivity<DB>() {

    lateinit var mViewModel: VM

    override fun init(savedInstanceState: Bundle?) {
        val vm = createViewModel()
        mViewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm)).get(vm::class.java)
        lifecycle.addObserver(mViewModel)
        initObserve()
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