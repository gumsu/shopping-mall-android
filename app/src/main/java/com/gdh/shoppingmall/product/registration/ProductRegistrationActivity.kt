package com.gdh.shoppingmall.product.registration

import android.os.Bundle
import android.view.MenuItem
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class ProductRegistrationActivity: BaseActivity<ProductRegistrationViewModel>() {

    override val viewModelType = ProductRegistrationViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProductRegistrationUI(getViewModel()).setContentView(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "상품 등록"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item?.let {
            when(item.itemId) {
                android.R.id.home -> onBackPressed()    // 백버튼을 눌렀을 때와 같은 효과
                else -> {}
            }
        }
        return true
    }
}