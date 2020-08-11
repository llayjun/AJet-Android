package com.millet.mylibrary.test

import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.millet.mylibrary.constant.ARouterPath
import com.millet.mylibrary.constant.Constant
import com.millet.mylibrary.base.vm.BaseViewModel
import com.millet.mylibrary.net.Api
import kotlinx.coroutines.*

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
//    fun getData() {
//        mShowDialog.setValue(true)
//        viewModelScope.launch {
//            val info = withContext(Dispatchers.IO) {
//                Api.apiInstance.jueJin("5562b419e4b00c57d9b94ae2", "20", "android")
//            }
//            mInfoBean.value = info
//            mShowDialog.setValue(false)
//        }
//    }

    private fun getDataCoroutines() {
        launchUI({
            val info = withContext(Dispatchers.IO) {
                Api.apiInstance.jueJin("5562b419e4b00c57d9b94ae2", "20", "android")
            }
            mInfoBean.value = info
        })
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
        getDataCoroutines()
    }

    fun webViewClick() {
        ARouter.getInstance().build(ARouterPath.ProtocolWeb)
            .withString(Constant.Key.WebViewUrl, "http://192.168.3.103:8084/login").navigation()
    }

    override fun onCreate() {
        super.onCreate()
    }

    // 使用协程
    fun coroutines() {
        // 协程三种方式
        // 第一种，用于单元测试，缺点阻塞线程，一般不实用
        runBlocking {

        }

        // 第二种，不会阻塞线程，缺点生命周期会和app一致，且不能取消
        GlobalScope.launch {

        }

        // 第三种，推荐使用
        // 1
        val coroutineScope = CoroutineScope(null!!)
        coroutineScope.launch(Dispatchers.IO) {
            // io 操作
            // xxx
            coroutineScope.launch(Dispatchers.Main) {
                // 更新ui操作
                // xxx
                coroutineScope.launch(Dispatchers.Default) {
                    // 操作
                    // xxx
                    // 这是个循环嵌套
                }
            }
        }

        // 2，同步的方式
        coroutineScope.launch(Dispatchers.Main) {
            // 主线程操作
            // xxx
            val result1 = withContext(Dispatchers.IO) {
                // io操作
                // xxx
            }
            // 主线程操作数据
            val result2 = withContext(Dispatchers.IO) {
                // io操作
                // xxx
            }
            // 主线程操作数据
            val result3 = withContext(Dispatchers.Default) {
                // default操作
                // xxx
            }
            // 主线程操作数据
            val image = getImage()
        }

        // 3多个请求同时
        coroutineScope.launch(Dispatchers.Main) {
            // 两个请求同时
            val result1 = async { getImage() }
            val result2 = async { getDataInfo() }
            result1.await()
            result2.await()
            // 或者
            val info = withContext(Dispatchers.IO) {
                val result3 = async { getResult3() }
                val result4 = async { getResult4() }
                result3.await() + result4.await()
            }
        }

    }

    private suspend fun getImage() = withContext(Dispatchers.IO) {
        // io操作
        // xxx
    }

    private suspend fun getDataInfo() = withContext(Dispatchers.Default) {
        // io操作
        // xxx
    }

    private fun getResult3(): Int { return 1 }
    private fun getResult4(): Int { return 1 }

}