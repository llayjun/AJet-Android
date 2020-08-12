package com.millet.mylibrary.test.adapter

import com.millet.mylibrary.R
import com.millet.mylibrary.base.ui.recyclerview.BaseBindingAdapter
import com.millet.mylibrary.bean.result.PersonUserInfoBean
import com.millet.mylibrary.databinding.ItemRvBinding

/**
 * @author  zyl
 * @date  2020/8/11 5:47 PM
 */
class TestAdapter: BaseBindingAdapter<ItemRvBinding ,PersonUserInfoBean>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_rv

    override fun onBindItem(binding: ItemRvBinding, item: PersonUserInfoBean, position: Int) {
        binding.personUserInfoBean = item
    }

}