package com.millet.mylibrary.test

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.millet.mylibrary.base.activity.BaseBvmActivity
import com.millet.mylibrary.R
import com.millet.mylibrary.base.ui.recyclerview.BaseAdapter
import com.millet.mylibrary.base.ui.recyclerview.BaseBindingAdapter
import com.millet.mylibrary.base.ui.recyclerview.BaseViewHolder
import com.millet.mylibrary.bean.result.PersonUserInfoBean
import com.millet.mylibrary.databinding.ActivityNewsBinding
import com.millet.mylibrary.databinding.ItemRvBinding
import com.millet.mylibrary.test.adapter.TestAdapter
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
        val infoList = ArrayList<PersonUserInfoBean>()
        infoList.add(PersonUserInfoBean("a", "b", "c", "d", 1, 2))
        infoList.add(PersonUserInfoBean("a1", "b1", "c1", "d1", 2, 21))
        infoList.add(PersonUserInfoBean("a2", "b2", "c2", "d2", 12, 22))
        ry_view.layoutManager = LinearLayoutManager(this)
        val testAdapter = object : BaseBindingAdapter<ItemRvBinding, PersonUserInfoBean>() {

            override fun getLayoutId(viewType: Int): Int = R.layout.item_rv

            override fun onBindItem(
                binding: ItemRvBinding,
                item: PersonUserInfoBean,
                position: Int
            ) {
                binding.personUserInfoBean = item
                binding.tvText.setOnClickListener {
                    ToastUtils.showShort("哈哈哈")
                }
            }

        }
        testAdapter.setBindItemClickListener { _: ItemRvBinding, _: PersonUserInfoBean, i: Int ->
            ToastUtils.showShort("点击了${i}")
        }
        ry_view.adapter = testAdapter
        testAdapter.refreshItems(infoList)

    }

    override fun createViewModel(): NewsViewModel = NewsViewModel()

}