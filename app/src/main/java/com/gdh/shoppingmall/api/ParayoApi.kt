package com.gdh.shoppingmall.api

import com.gdh.shoppingmall.api.request.SignInRequest
import com.gdh.shoppingmall.api.request.SignUpRequest
import com.gdh.shoppingmall.api.response.ApiResponse
import com.gdh.shoppingmall.api.response.ProductImageUploadResponse
import com.gdh.shoppingmall.api.response.SignInResponse
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * 비동기 호출을 위해 api 인터페이스를 suspend 함수로 선언한다.
 * 반환값은 API 응답 타입을 정의하면 된다.
 * instance라는 정적 필드에 Retrofit이 생성해준 ParayoApi 인터페이스의 구현체를 넣어준다.
 *
 * @Body 애노테이션은 파라미터 값을 HTTP의 요청 본문에 쓰도록 지시한다.
 * URI에 노출되지 않음
 */

interface ParayoApi {

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>

    companion object {
        val instance = ApiGenerator()
            .generate(ParayoApi::class.java)
    }

    @POST("/api/v1/users")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): ApiResponse<Void>

    @POST("/api/v1/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest) : ApiResponse<SignInResponse>

    @Multipart
    @POST("/api/v1/product_images")
    suspend fun uploadProductImages(@Part images: MultipartBody.Part) : ApiResponse<ProductImageUploadResponse>
}