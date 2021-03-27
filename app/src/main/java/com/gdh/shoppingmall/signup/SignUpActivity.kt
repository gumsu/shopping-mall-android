package com.gdh.shoppingmall.signup

import android.os.Bundle
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

/**
 * signUpActivityUI 의 생성자에서 SignUpViewModel을 필요로 하므로 getViewModel()을 통해
 * SignUpViewModel 객체 주입
 */

class SignUpActivity : BaseActivity<SignUpViewModel>() {
    override val viewModelType = SignUpViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SignUpActivityUI(getViewModel()).setContentView(this)
    }
}