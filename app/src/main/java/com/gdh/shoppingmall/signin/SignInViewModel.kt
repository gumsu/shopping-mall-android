package com.gdh.shoppingmall.signin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.api.request.SignInRequest
import com.gdh.shoppingmall.api.response.ApiResponse
import com.gdh.shoppingmall.api.response.SignInResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error

/**
 * 로그인에 필요한 email과 password 필드를 함께 초기화
 */

class SignInViewModel(app: Application) : BaseViewModel(app) {
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    suspend fun signIn() {
        val request = SignInRequest(email.value, password.value)
        if (isNotValidSignIn(request)) return

        try {
            val response = requestSignIn(request)
            onSignInResponse(response)
        } catch (e: Exception) {
            error("signIn Error", e)
            toast("알 수 없는 오류가 발생했습니다.")
        }
    }

    private fun isNotValidSignIn(request: SignInRequest) =
        when {
            request.isNotValidEmail() -> {
                toast("이메일 형식이 정확하지 않습니다.")
                true
            }
            request.isNotValidPassword() -> {
                toast("비밀번호는 8자 이상 20자 이하로 입력해주세요.")
                true
            }
            else -> false
        }

    private suspend fun requestSignIn(request: SignInRequest) =
        withContext(Dispatchers.IO) {
            ParayoApi.instance.signIn(request)
        }

    private fun onSignInResponse(response: ApiResponse<SignInResponse>) {
        if(response.success) {
            toast("로그인되었습니다.")
            //TODO. 상품 리스트 화면으로 이동
        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }
}