package com.millet.mylibrary.constant

import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.millet.mylibrary.R
import com.ruffian.library.widget.RImageView

/**
 * @author  zyl
 * @date  2020/7/12 6:51 PM
 */
object BindingAdapter {

    // 通用的BindingAdapter
    // 显示图片
    @BindingAdapter(value = ["imageUrl", "errorImageUrl"], requireAll = false)
    @JvmStatic
    fun setImageUrl(view: RImageView, imageUrl: String?, @DrawableRes errorResourceId: Int) {
        Glide.with(view.context).load(imageUrl)
            .error(errorResourceId)
            .placeholder(R.mipmap.ic_launcher)
            .into(view)
    }

}