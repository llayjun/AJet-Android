package com.millet.mylibrary.widget.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by LiLiang on 2017/6/24.
 */
class SpacesItemDecoration : ItemDecoration {
    private var space = 0
    private var left = 0
    private var top = 0
    private var right = 0
    private var bottom = 0

    constructor(space: Int) {
        this.space = space
    }

    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = if (space == 0) left else space
        outRect.right = if (space == 0) right else space
        outRect.bottom = if (space == 0) bottom else space
        outRect.top = if (space == 0) top else space
    }
}