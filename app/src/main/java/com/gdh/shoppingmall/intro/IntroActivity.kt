package com.gdh.shoppingmall.intro

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.signin.SignInActivity
import com.gdh.shoppingmall.signup.SignUpActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import java.lang.Exception

/**
 * GlobalScope.launch -> 메인 쓰레드에서 비동기 작업을 시작하고
 * 코루틴 블록 내부에서 회원 가입 화면으로 전환을 시켜준다.
 *
 * delay(1000) -> 1초간 딜레이
 *
 * startActivity<SignUpActivity>() -> anko에서 제공하는 헬퍼 함수
 *
 * finish() -> 뒤로가기 버튼을 눌렀을 때 다시 보여지면 안되므로 액티비티를 종료시켜준다.
 */

class IntroActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = IntroActivityUI()
        ui.setContentView(this)

        GlobalScope.launch {
            delay(1000)
            startActivity<SignInActivity>()
            finish()
        }
    }
}