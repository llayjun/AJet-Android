package com.millet.mylibrary.net

import com.blankj.utilcode.util.Utils
import com.millet.mylibrary.net.interceptor.LoggingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

class Api : BaseApi() {

    /**
     * 静态内部类单例
     */
    private object ApiHolder {
        private val api = Api()
        val apiService: ApiService
           get() = api.initRetrofit(ApiService.BASE_URL)!!.create(
                ApiService::class.java
            )
    }

    /**
     * 做自己需要的操作
     */
    override fun setClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)// 连接超时时间
            .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)// 设置写操作超时时间
            .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)// 设置读操作超时时间
            .retryOnConnectionFailure(true)
            .cache(
                Cache(
                    File(Utils.getApp().cacheDir.toString() + "HttpCache"),
                    1024L * 1024 * 100
                )
            ).build()
    }

    companion object {

        // time
        private const val DEFAULT_CONNECT_TIME = 10L
        private const val DEFAULT_WRITE_TIME = 30L
        private const val DEFAULT_READ_TIME = 30L

        val apiInstance: ApiService
            get() = ApiHolder.apiService
    }
}