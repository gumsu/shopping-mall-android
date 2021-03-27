package com.gdh.shoppingmall.api.request

import android.util.Patterns

/**
 * 회원 가입 API 호출하는 부분
 * 서버에서 개발해두었던 회원 가입 API 스펙에 맞게 파라미터로 쓰일 SignUpRequest를 추가하고 ParayoApi에 API 함수 선언
 *
 * 각각의 필드 값을 검증하는 함수를 포함
 * 안드로이드 SDK에는 Patterns 유틸리티에 이메일 검증을 도와주는 EMAIL_ADDRESS 정규표현식이 준비되어 있다.
 */

class SignUpRequest(
    val email: String?,
    val password: String?,
    val name: String?
) {
    fun isNotValidEmail() = email.isNullOrBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isNotValidPassword() = password.isNullOrBlank() || password.length !in 8..20
    fun isNotValidName() = name.isNullOrBlank() || name.length !in 2..20
}