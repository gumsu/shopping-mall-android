package com.gdh.shoppingmall.api

import android.content.Intent
import com.gdh.shoppingmall.App
import com.gdh.shoppingmall.common.Prefs
import com.gdh.shoppingmall.signin.SignInActivity
import okhttp3.Interceptor
import okhttp3.Response
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.intentFor

/**
 * 토큰 갱신 요청 전에 refreshToken을 추가하고 토큰 갱신 요청의 응답 코드가 401인 경우 로그인 화면으로 이동시켜주는 역할
 *
 * SharedPreference에 refreshToken이 존재하는 경우 Authorization 헤더에 추가해준다.
 */

class TokenRefreshInterceptor: Interceptor, AnkoLogger {

    override fun intercept(chain: Interceptor.Chain): Response {
        debug("토큰 갱신 요청")
        val original = chain.request()
        val request = original.newBuilder().apply {
            Prefs.refreshToken?.let { header("Authorization", it) }
            method(original.method(), original.body())
        }.build()

        val response = chain.proceed(request)

        if(response.code() == 401) {
            App.instance.run {
                val intent = intentFor<SignInActivity>().apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
        }

        return response
    }
}