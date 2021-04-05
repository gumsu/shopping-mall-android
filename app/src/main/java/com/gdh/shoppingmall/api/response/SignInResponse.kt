package com.gdh.shoppingmall.api.response

data class SignInResponse(
    val token: String,
    val refreshToken: String,
    val userName: String,
    val userId: Long
) {
}