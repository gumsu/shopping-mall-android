package com.gdh.shoppingmall.product.detail

import net.codephobia.ankomvvm.components.BaseActivity
import kotlin.reflect.KClass

class ProductDetailActivity : BaseActivity<ProductDetailViewModel>() {

    override val viewModelType = ProductDetailViewModel::class
}