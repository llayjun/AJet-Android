package com.millet.mylibrary.test

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.base.activity.BaseBvmActivity
import com.millet.mylibrary.R
import com.millet.mylibrary.databinding.ActivityNewsBinding
import kotlinx.android.synthetic.main.activity_news.*

class NewBvmActivity : BaseBvmActivity<NewsViewModel, ActivityNewsBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_news

    override fun initData(savedInstanceState: Bundle?) {
        //允许绑定观察ViewModel中的LiveData数据，当LiveData数据更新时，布局会自动更新数据
        mDataBinding.viewModels = mViewModel
        mViewModel.mInfoBean.observe(this, Observer {
            ToastUtils.showShort(it.toString())
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        color_tv.text = ""
        color_tv.appendText("1", Color.RED, 12)
            .appendSpace()
            .appendText("标题", Color.BLUE, 16, true)
            .appendSpace()
            .appendText("内容", Color.BLACK, 14)
            .appendText("¥", Color.RED, 12)
    }

    override fun loadData(savedInstanceState: Bundle?) {

    }

    override fun createViewModel(): NewsViewModel = NewsViewModel()

}