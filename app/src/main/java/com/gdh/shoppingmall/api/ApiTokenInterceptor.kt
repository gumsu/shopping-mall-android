package com.gdh.shoppingmall.api

import com.gdh.shoppingmall.common.Prefs
import okhttp3.Interceptor
import okhttp3.Response
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * 헤더에 API 토큰을 추가하는 역할
 *
 * chain.request() -> 원래의 요청 객체를 가져온다.
 * SharedPreference 에 토큰 값이 있는 경우 Authorization 헤더에 토큰을 추가
 * 새 요청에 HTTP 메소드와 바디를 넣는다.
 * 새 요청을 기반으로 HTTP 요청에 대한 응답을 생성해 반환한다.
 */

class ApiTokenInterceptor: Interceptor, AnkoLogger {

    override fun intercept(chain: Interceptor.Chain): Response {
        debug("API 요청")
        val original = chain.request()
        val request = original.newBuilder().apply {
            Prefs.token?.let { header("Authorization", it) }
            method(original.method(), original.body())
        }.build()

        return chain.proceed(request)
    }
}