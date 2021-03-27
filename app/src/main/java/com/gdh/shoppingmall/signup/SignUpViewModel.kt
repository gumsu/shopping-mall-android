package com.gdh.shoppingmall.signup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

/**
 * 뷰와 모델 간의 연결고리 역할
 * MVVM에서는 대개 뷰와 뷰모델이 가진 데이터의 바인딩을 자동화하기 위해 대부분의 데이터를 Observable 이나 LiveData 등으로 선언
 * MVVM 탬플릿 라이브러리에서는 MutableLiveData 사용
 */

class SignUpViewModel(app: Application) : BaseViewModel(app) {

    val email = MutableLiveData("")
    val name = MutableLiveData("")
    val password = MutableLiveData("")
}