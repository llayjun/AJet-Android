package com.millet.mylibrary.base.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: Albert Li
 * @contact: albertlii@163.com
 * @time: 2020/5/30 9:20 PM
 * @description: RecyclerView Adapter封装
 * @since: 1.0.0
 */
abstract class BaseAdapter<T> :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var items = ArrayList<T>()

    private var itemClickListener: (holder: BaseViewHolder, item: T, position: Int) -> Unit =
        { _: BaseViewHolder, _: T, _: Int -> }

    public fun setItemClickListener(itemClickListener: (holder: BaseViewHolder, item: T, position: Int) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItem(holder, items[position], position)
        holder.itemView.setOnClickListener {
            itemClickListener(
                holder,
                items[position],
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @LayoutRes
    abstract fun getLayoutId(viewType: Int): Int

    abstract fun onBindItem(holder: BaseViewHolder, item: T, position: Int)

    fun getItems(): ArrayList<T> {
        return items
    }

    fun setItems(items: ArrayList<T>) {
        this.items = items
    }

    fun refreshItems(item: ArrayList<T>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    fun refreshItem(position: Int, item: T) {
        if (itemCount > position) {
            items[position] = item
            notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int) {
        if (itemCount > position) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        items.clear()
    }
}