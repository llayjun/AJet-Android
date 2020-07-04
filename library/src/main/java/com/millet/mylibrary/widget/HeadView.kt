package com.millet.mylibrary.widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.millet.mylibrary.R
import kotlinx.android.synthetic.main.view_header.view.*

class HeadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val leftIcon: Drawable?
    private val rightIcon: Drawable?
    private val leftString: String?
    private val middleString: String?
    private val rightString: String?
    private val lineShow: Boolean?

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadView)
        leftIcon = typedArray.getDrawable(R.styleable.HeadView_left_icon)
        rightIcon = typedArray.getDrawable(R.styleable.HeadView_right_icon)
        leftString = typedArray.getString(R.styleable.HeadView_left_text)
        rightString = typedArray.getString(R.styleable.HeadView_right_text)
        middleString = typedArray.getString(R.styleable.HeadView_middle_text)
        lineShow = typedArray.getBoolean(R.styleable.HeadView_bottom_line, false)
        typedArray.recycle()
        initView(context)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.view_header, this)
        if (lineShow!!) {
            view_line.visibility = View.VISIBLE
        } else {
            view_line.visibility = View.GONE
        }
        left_image.setOnClickListener {
            (getContext() as Activity).finish()
        }
        left_text.setOnClickListener {
            (getContext() as Activity).finish()
        }
//        if (null != leftIcon) {
//            left_image.setImageDrawable(leftIcon)
//            left_image.visibility = View.VISIBLE
//        } else {
//            left_image.visibility = View.GONE
//        }
        if (!TextUtils.isEmpty(leftString)) {
            left_text.text = leftString
            left_text.visibility = View.VISIBLE
        } else {
            left_text.visibility = View.GONE
        }
        if (null != rightIcon) {
            right_image.setImageDrawable(rightIcon)
            right_image.visibility = View.VISIBLE
        } else {
            right_image.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(rightString)) {
            right_text.text = rightString
            right_text.visibility = View.VISIBLE
        } else {
            right_text.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(middleString)) {
            middle_title.text = middleString
            middle_title.visibility = View.VISIBLE
        } else {
            middle_title.visibility = View.GONE
        }
    }

    fun setLeftIconClickListener(onClickListener: OnClickListener?) {
        left_image?.setOnClickListener(onClickListener)
    }

    fun setLeftTextClickListener(onClickListener: OnClickListener?) {
        left_text!!.setOnClickListener(onClickListener)
    }

    fun setRightIconClickListener(onClickListener: OnClickListener?) {
        right_image?.setOnClickListener(onClickListener)
    }

    fun setRightTextClickListener(onClickListener: OnClickListener?) {
        right_text?.setOnClickListener(onClickListener)
    }

    fun setMiddleString(string: String?) {
        middle_title?.text = string
    }

}