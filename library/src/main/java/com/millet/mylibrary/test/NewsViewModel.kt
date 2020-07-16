package com.millet.mylibrary.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.millet.mylibrary.constant.ARouterPath
import com.millet.mylibrary.constant.Constant
import com.millet.mylibrary.mvvm.vm.BaseViewModel
import com.millet.mylibrary.net.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel : BaseViewModel() {

    var mInfoBean = MutableLiveData<String>("哈哈")

    // 多个协程
//     suspend fun loadInfo() {
//        coroutineScope {
//            repeat(100) {
//                launch {
//
//                }
//            }
//        }
//    }

    // 协程
    fun getData() {
        mShowDialog.setValue(true)
        viewModelScope.launch {
            val info = withContext(Dispatchers.IO) {
                Api.apiInstance.jueJin("5562b419e4b00c57d9b94ae2", "20", "android")
            }
            mInfoBean.value = info
            mShowDialog.setValue(false)
        }
    }

    // raxjava
//    fun getData() {
//        mShowDialog?.setValue(true)
//        val disposable = Api.apiInstance
//            .jueJin("5562b419e4b00c57d9b94ae2", "20", "android")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                mShowDialog?.setValue(false)
//                mNewsBean.value = it
//            }, {
//                mShowDialog?.setValue(false)
//                mThrowable?.value = it
//            })
//        addDisposable(disposable)
//    }

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