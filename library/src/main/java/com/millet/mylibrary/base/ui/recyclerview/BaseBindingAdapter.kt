package com.millet.mylibrary.base.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author: Albert Li
 * @contact: albertlii@163.com
 * @time: 2020/5/30 10:25 PM
 * @description: 基于DataBinding的RecyclerView Adapter封装
 * @since: 1.0.0
 */
abstract class BaseBindingAdapter<B : ViewDataBinding, T>() :
    BaseAdapter<T>() {

    private var bindItemClickListener: (binding: B, item: T, position: Int) -> Unit =
        { _: B, _: T, _: Int -> }

    public fun setBindItemClickListener(bindItemClickListener: (binding: B, item: T, position: Int) -> Unit) {
        this.bindItemClickListener = bindItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )
        return BaseViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binding: B = DataBindingUtil.getBinding(holder.itemView)!!
        onBindItem(binding, getItems()[position], position)
        binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            bindItemClickListener(
                binding,
                getItems()[position],
                position
            )
        }
    }

    override fun onBindItem(holder: BaseViewHolder, item: T, position: Int) {
        // 无用
    }

    abstract fun onBindItem(binding: B, item: T, position: Int)

}