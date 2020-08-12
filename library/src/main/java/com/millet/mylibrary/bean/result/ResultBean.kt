package com.millet.mylibrary.bean.result

/**
 * @author  zyl
 * @date  2020/7/4 9:54 AM
 */
// 雇员登录信息
data class PersonLoginResultBean(
    val account: String,
    val bankCard: Any,
    val cardFlag: Int,
    val cardName: Any,
    val createTime: String,
    val id: Int,
    val idCard: Any,
    val idCardF: Any,
    val idCardZ: Any,
    val lastLoginIp: Any,
    val lastLoginTime: Any,
    val openId: String,
    val password: Any
)

// 用户基本信息
data class PersonUserInfoBean(
    val birth: String,
    val headimg: String,
    val introduction: String,
    val nickName: String,
    val sex: Int,
    val userId: Int
) {
    val sexType: String
        get() {
            return when (sex) {
                1 -> "男"
                2 -> "女"
                else -> "秘密"
            }
        }
}