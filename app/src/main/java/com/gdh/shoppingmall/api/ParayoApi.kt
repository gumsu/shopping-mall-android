package com.gdh.shoppingmall.api

import com.gdh.shoppingmall.api.response.ApiResponse
import retrofit2.http.GET

/**
 * 비동기 호출을 위해 api 인터페이스를 suspend 함수로 선언한다.
 * 반환값은 API 응답 타입을 정의하면 된다.
 * instance라는 정적 필드에 Retrofit이 생성해준 ParayoApi 인터페이스의 구현체를 넣어준다.
 */

interface ParayoApi {

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>

    companion object {
        val instance = ApiGenerator()
            .generate(ParayoApi::class.java)
    }
}