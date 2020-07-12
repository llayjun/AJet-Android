package com.millet.mylibrary.mvvm

import android.app.Application
import com.blankj.utilcode.util.Utils

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}