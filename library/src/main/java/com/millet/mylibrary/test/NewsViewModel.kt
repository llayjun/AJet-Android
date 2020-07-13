package com.millet.mylibrary.test

import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.millet.mylibrary.constant.ARouterPath
import com.millet.mylibrary.constant.Constant
import com.millet.mylibrary.mvvm.vm.BaseViewModel
import com.millet.mylibrary.net.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsViewModel : BaseViewModel() {

    var mInfoBean = MutableLiveData<String>()

    private fun getData() {
        mShowDialog.setValue(true)
        val disposable = Api.apiInstance
            .jueJin("5562b419e4b00c57d9b94ae2", "20", "android")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mShowDialog.setValue(false)
                mInfoBean.value = it
            }, {
                mShowDialog.setValue(false)
                mThrowable.value = it
            })
        addDisposable(disposable)
    }

    fun getDataClick() {
        getData()
    }

    fun webViewClick() {
        ARouter.getInstance().build(ARouterPath.ProtocolWeb).withString(Constant.Key.WebViewUrl, "http://www.baidu.com").navigation()
    }

    override fun onCreate() {
        super.onCreate()
    }

}