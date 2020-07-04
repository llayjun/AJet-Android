package com.millet.mylibrary.bean.request

/**
 * @author  zyl
 * @date  2020/7/3 5:48 PM
 */
data class SmsCodeRequestBean(val phoneNum: String?)

data class LoginRequestBean(val phoneNum: String?, val smsCode: String?)

data class PersonUserInfoRequestBean(val userId: String?)

data class UpdateUserInfoRequestBean(
    val birth: String,
    val headimg: String,
    val introduction: String,
    val nickName: String,
    val sex: Int,
    val userId: Int
)