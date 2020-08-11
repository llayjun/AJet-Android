package com.millet.mylibrary.constant

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.millet.mylibrary.R
import com.ruffian.library.widget.RImageView

/**
 * @author  zyl
 * @date  2020/7/12 6:51 PM
 */
object BindingAdapter {

    // 通用的BindingAdapters
    // 显示图片
    @BindingAdapter(value = ["imageUrl", "isNoCache"], requireAll = false)
    @JvmStatic
    fun setImageUrl(view: RImageView, imageUrl: String?, isNoCache: Boolean = false) {
        if (isNoCache) {
            Glide.with(view.context).load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .apply(RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(view)
        } else {
            Glide.with(view.context).load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(view)
        }
    }
}