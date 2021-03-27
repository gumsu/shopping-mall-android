package com.gdh.shoppingmall.signup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.api.request.SignUpRequest
import com.gdh.shoppingmall.api.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error

/**
 * 뷰와 모델 간의 연결고리 역할
 * MVVM에서는 대개 뷰와 뷰모델이 가진 데이터의 바인딩을 자동화하기 위해 대부분의 데이터를 Observable 이나 LiveData 등으로 선언
 * MVVM 탬플릿 라이브러리에서는 MutableLiveData 사용
 *
 * - 이메일, 이름, 비밀번호가 조건에 맞게 들어갔는지 검사
 * - 회원 가입 API 호출
 * - 가입 실패 혹은 성공 메시지를 경우에 알맞게 출력
 * - 성공 시에는 로그인 화면으로 전환
 *
 * isNotValidSignUp 함수는 요청 파라미터가 정확하게 입력되었는지 검증하는 함수
 * signUp 함수의 초반에 이 함수를 호출해 파라미터들이 올바르게 입력되지 않았다면 즉시 빠져나오도록 구현
 *
 * requestSignUp 함수는 회원 가입 API를 호출해주는 코드
 * 네트워크 요청 시에는 언제든 오류가 발생할 가능성이 존재하므로 바깥쪽에서 try-catch문으로 묶어 오류메시지를 표시
 * UI가 포함된 애플리케이션을 개발할 때에는 네트워크 요청이 일어나는 동안 UI가 멈춘 것처럼 보일 수 있기 때문에
 * 네트워크 요청은 비동기로 실행해야 한다.
 * withContext 코루틴 빌더를 사용하면 현재 스레드를 블록킹하지 않고 새로운 코루틴을 시작할 수 있다.
 * 이 블록 내의 코드는 IO 쓰레드에서 비동기로 실행되게 된다. 이 함수는 코루틴 내부에서 실행되거나 suspend 함수 내부에서만
 * 실행되어야만 하기 때문에 suspend로 선언해야 한다. 또한 suspend 함수의 호출도 다른 코루틴 내부에서 일어나거나,
 * 또다른 suspend 함수 내에서 실행되어야 하기 때문에 signup() 함수도 suspend로 정의해야 한다.
*/

class SignUpViewModel(app: Application) : BaseViewModel(app) {

    val email = MutableLiveData("")
    val name = MutableLiveData("")
    val password = MutableLiveData("")

    suspend fun signUp() {
        val request = SignUpRequest(email.value, password.value, name.value)
        if (isNotValidSignUp(request))
            return
        try {
            val response = requestSignUp(request)
            onSignUpResponse(response)
        } catch (e: Exception) {
            error("SignUp Error", e)
            toast("알 수 없는 오류가 발생했습니다.")
        }
    }

    private fun isNotValidSignUp(signUpRequest: SignUpRequest) =
        when {
            signUpRequest.isNotValidEmail() -> {
                toast("이메일 형식이 정확하지 않습니다.")
                true
            }
            signUpRequest.isNotValidPassword() -> {
                toast("비밀번호는 8자 이상 20자 이하로 입력해주세요.")
                true
            }
            signUpRequest.isNotValidName() -> {
                toast("이름은 2자 이상 20자 이하로 입력해주세요.")
                true
            }
            else -> false
        }

    private suspend fun requestSignUp(request: SignUpRequest) =
        withContext(Dispatchers.IO) {
            ParayoApi.instance.signUp(request)
        }

    private fun onSignUpResponse(response: ApiResponse<Void>) {
        if (response.success) {
            toast("회원 가입이 되었습니다. 로그인 후 이용해주세요.")
            finishActivity()
        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }
}