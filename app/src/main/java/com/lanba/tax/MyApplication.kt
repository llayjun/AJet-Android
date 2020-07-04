package com.lanba.tax

import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.millet.mylibrary.base.BaseApplication
import com.tencent.mmkv.MMKV

/**
 * @author  zyl
 * @date  2020/6/29 8:16 PM
 */
class MyApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        // mmkv
        MMKV.initialize(this)
        // 初始化ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

}