package com.millet.mylibrary.retrofit

import com.millet.mylibrary.bean.request.LoginRequestBean
import com.millet.mylibrary.bean.request.PersonUserInfoRequestBean
import com.millet.mylibrary.bean.request.SmsCodeRequestBean
import com.millet.mylibrary.bean.request.UpdateUserInfoRequestBean
import com.millet.mylibrary.bean.result.BaseBean
import com.millet.mylibrary.bean.result.PersonLoginResultBean
import com.millet.mylibrary.bean.result.PersonUserInfoBean
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    // 掘金接口
    @GET("get_entry_by_timeline")
    fun jueJin(
        @Query("category") category: String?,
        @Query("limit") limit: String?,
        @Query("src") src: String?
    ): Observable<String?>

    // 个人用户
    // 获取验证码
    @POST("freeemployment/api/sendSmsCode")
    fun sendSmsCode(@Body smsCodeRequestBean: SmsCodeRequestBean?): Observable<BaseBean<String>?>

    // 雇员登录
    @POST("freeemployment/api/employmentLogin")
    fun personLogin(@Body loginRequestBean: LoginRequestBean?): Observable<BaseBean<PersonLoginResultBean>?>

    // 获取用户基本信息
    @POST("freeemployment/api/userInfoSearch")
    fun getUserInfo(@Body personUserInfoRequestBean: PersonUserInfoRequestBean): Observable<BaseBean<PersonUserInfoBean>>

    // 更新用户基本信息
    @POST("freeemployment/api/updateUserInfo")
    fun updateUserInfo(@Body updateUserInfoRequestBean: UpdateUserInfoRequestBean): Observable<BaseBean<PersonUserInfoBean>>

    companion object {
        const val BASE_URL = "http://timeline-merger-ms.juejin.im/v1/"
    }
}