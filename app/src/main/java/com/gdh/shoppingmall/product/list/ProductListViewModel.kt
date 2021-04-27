package com.gdh.shoppingmall.product.list

import android.app.Application
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

/**
 * onClickItem() -> 상품 리스트의 아이템이 클릭되었을 때 호출될 함수
 */

class ProductListViewModel(app: Application) : BaseViewModel(app) {

    fun onClickItem(id: Long?) {
        toast("click $id")
    }
}