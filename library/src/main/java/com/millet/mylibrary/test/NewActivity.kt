package com.millet.mylibrary.test

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.base.BaseActivity
import com.millet.mylibrary.R
import com.millet.mylibrary.constant.ARouterPath
import com.millet.mylibrary.constant.Constant
import com.millet.mylibrary.databinding.ActivityNewsBinding
import kotlinx.android.synthetic.main.activity_news.*

class NewActivity : BaseActivity<NewsViewModel, ActivityNewsBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_news

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun loadData(savedInstanceState: Bundle?) {
        mDataBinding?.viewModels = mViewModel
        //允许绑定观察ViewModel中的LiveData数据，当LiveData数据更新时，布局会自动更新数据
        mDataBinding?.lifecycleOwner = this
        mViewModel?.mNewsBean?.observe(this, Observer {
            ToastUtils.showShort(it.toString())
        })
    }

    override fun initViewModel(): NewsViewModel {
        return ViewModelProvider(this).get(NewsViewModel::class.java)
    }

}