package com.millet.mylibrary.base.vm

import androidx.lifecycle.MutableLiveData
import com.millet.mylibrary.bean.DialogBean

class DialogLiveData<T> : MutableLiveData<T>() {

    private val bean: DialogBean = DialogBean()

    fun setValue(isShow: Boolean) {
        bean.isShow = isShow
        value = bean as T
    }

}