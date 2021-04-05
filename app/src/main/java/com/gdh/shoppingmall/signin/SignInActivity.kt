package com.gdh.shoppingmall.signin

import android.os.Bundle
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class SignInActivity : BaseActivity<SignInViewModel>() {
    override val viewModelType = SignInViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SignInActivityUI(getViewModel()).setContentView(this)
    }
}