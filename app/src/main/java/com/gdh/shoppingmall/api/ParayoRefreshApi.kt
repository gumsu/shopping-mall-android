package com.gdh.shoppingmall.api

import com.gdh.shoppingmall.api.response.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 토큰 갱신 API를 정의한 인터페이스 작성
 */

interface ParayoRefreshApi {

    @POST("/api/v1/refresh_token")
    suspend fun refreshToken(
        @Query("grant_type") grantType: String = "refresh_token") : ApiResponse<String>

    companion object {
        val instance = ApiGenerator().generateRefreshClient(ParayoRefreshApi::class.java)
    }
}