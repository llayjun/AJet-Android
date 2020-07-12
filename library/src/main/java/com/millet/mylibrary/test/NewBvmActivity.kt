package com.millet.mylibrary.test

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.mvvm.BaseBvmActivity
import com.millet.mylibrary.R
import com.millet.mylibrary.databinding.ActivityNewsBinding

class NewBvmActivity : BaseBvmActivity<NewsViewModel, ActivityNewsBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_news

    override fun initData(savedInstanceState: Bundle?) {
        //允许绑定观察ViewModel中的LiveData数据，当LiveData数据更新时，布局会自动更新数据
        mDataBinding.viewModels = mViewModel
        mViewModel.mNewsBean.observe(this, Observer {
            ToastUtils.showShort(it.toString())
        })
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun loadData(savedInstanceState: Bundle?) {

    }

    override fun createViewModel(): NewsViewModel = NewsViewModel()

}