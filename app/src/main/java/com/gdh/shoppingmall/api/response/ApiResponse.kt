package com.gdh.shoppingmall.api.response

/**
 * 자체적으로 오류 응답을 만들어내야 하는 상황을 대비하여 error() 함수 추가
 * reified는 inline 함수에 붙을 수 있는 특별한 키워드로써
 * ApiResponse.error<Type>(...) 형태로 호출하도록 만든다.
 */

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        inline fun <reified T> error(message: String? = null) =
            ApiResponse(false, null as T?, message)
    }
}
