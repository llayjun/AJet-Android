package com.millet.mylibrary.widget.decoration

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by LiLiang on 2017/5/18.
 */
class CommonItemDecoration : ItemDecoration {
    private var mDivider: Drawable?
    private var mOrientation = 0
    private var hasHeader = false
    private var hasFooter = false

    constructor(context: Context, orientation: Int) {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }

    constructor(
        context: Context?,
        orientation: Int,
        drawable: Drawable?
    ) {
        mDivider = drawable
        setOrientation(orientation)
    }

    constructor(
        context: Context?,
        orientation: Int,
        drawable: Drawable?,
        hasHeader: Boolean
    ) {
        mDivider = drawable
        this.hasHeader = hasHeader
        setOrientation(orientation)
    }

    constructor(
        context: Context?,
        orientation: Int,
        drawable: Drawable?,
        hasHeader: Boolean,
        hasFooter: Boolean
    ) {
        mDivider = drawable
        this.hasHeader = hasHeader
        this.hasFooter = hasFooter
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)) { "invalid orientation" }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    fun drawVertical(c: Canvas?, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            if (hasHeader && i == 0) {
                continue
            }
            val child = parent.getChildAt(i)
            val v = RecyclerView(parent.context)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c!!)
        }
    }

    fun drawHorizontal(c: Canvas?, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c!!)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        itemPosition: Int,
        parent: RecyclerView
    ) {
        if (itemPosition == parent.adapter!!.itemCount - 1 || hasHeader && itemPosition == 0
            || hasFooter && itemPosition == parent.adapter!!.itemCount - 2
        ) {
            return
        }
        if (mOrientation == VERTICAL_LIST) {
            outRect[0, 0, 0] = mDivider!!.intrinsicHeight
        } else {
            outRect[0, 0, mDivider!!.intrinsicWidth] = 0
        }
    }

    companion object {
        private val ATTRS = intArrayOf(
            R.attr.listDivider
        )
        const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        const val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }
}