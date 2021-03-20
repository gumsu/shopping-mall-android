package com.gdh.shoppingmall.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * retrofit과 연계할 HTTP 통신 객체 생성 - okhttp 클라이언트 사용
 * API 응답 결과를 로그로 확인하기 위해 별도로 HTTP 바디를 로깅해주도록 설정
 */

class ApiGenerator {
    fun <T> generate(api: Class<T>): T = Retrofit.Builder()
        .baseUrl(HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient())
        .build()
        .create(api)

    private fun httpClient() = OkHttpClient.Builder().apply {
        addInterceptor(httpLoggingInterceptor())
    }.build()

    private fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    companion object {
        const val HOST = "http://10.0.2.2:8080"
    }
}