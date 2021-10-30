package com.gdh.shoppingmall.api.request

import android.util.Patterns
import com.gdh.shoppingmall.common.Prefs

class SignInRequest(
    val email: String?,
    val password: String?,
    val fcmToken: String? = Prefs.fcmToken
) {
    fun isNotValidEmail() =
        email.isNullOrBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isNotValidPassword() =
        password.isNullOrBlank() || password.length !in 8..20
}