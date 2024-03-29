package com.gdh.shoppingmall.api.request

import android.util.Patterns
import com.gdh.shoppingmall.common.Prefs

/**
 * 회원 가입 API 호출하는 부분
 * 서버에서 개발해두었던 회원 가입 API 스펙에 맞게 파라미터로 쓰일 SignUpRequest를 추가하고 ParayoApi에 API 함수 선언
 *
 * 각각의 필드 값을 검증하는 함수를 포함
 * 안드로이드 SDK에는 Patterns 유틸리티에 이메일 검증을 도와주는 EMAIL_ADDRESS 정규표현식이 준비되어 있다.
 *
 * FCM 토큰이 생성되면 토큰을 저장하고 있다가 API에서 토큰 값을 사용하기 위해 프로퍼티 추가가 */

class SignUpRequest(
    val email: String?,
    val password: String?,
    val name: String?,
    val fcmToken: String? = Prefs.fcmToken
) {
    fun isNotValidEmail() = email.isNullOrBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isNotValidPassword() = password.isNullOrBlank() || password.length !in 8..20
    fun isNotValidName() = name.isNullOrBlank() || name.length !in 2..20
}