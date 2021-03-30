package com.gdh.shoppingmall.signin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

/**
 * 로그인에 필요한 email과 password 필드를 함께 초기화
 */

class SignInViewModel(app: Application) : BaseViewModel(app) {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
}