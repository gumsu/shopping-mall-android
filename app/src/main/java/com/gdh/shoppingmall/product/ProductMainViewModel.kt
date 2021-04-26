package com.gdh.shoppingmall.product

import android.app.Application
import android.content.Intent
import com.gdh.shoppingmall.product.registration.ProductRegistrationActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

/**
 * flags FLAG_ACTIVITY_SINGLE_TOP -> 이미 액티비티가 실행 중인 경우 새로운 액티비티를 실행하지 않는다.
 * 상품 등록 버튼을 두 번 누를 경우 액티비티가 두 번 뜨는 것을 방지하기 위한 플래그로 사용
 */

class ProductMainViewModel(app: Application) : BaseViewModel(app) {
    fun openRegistrationActivity() {
        startActivity<ProductRegistrationActivity>() {flags = Intent.FLAG_ACTIVITY_SINGLE_TOP}
    }
}