package com.gdh.shoppingmall.product.search

import android.app.Application
import android.content.Intent
import com.gdh.shoppingmall.product.detail.ProductDetailActivity
import com.gdh.shoppingmall.product.list.ProductListItemDataSource
import com.gdh.shoppingmall.product.list.ProductListPagedAdapter
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

/**
 * 검색 결과 페이지에서는 키워드 변경이 불가능하므로 keyword 변수를 String 형태로 가진다.
 * LiveData<PagedList<T>> 는 ProductListViewModel에서 사용한 것을 재사용한다.
 * 검색 목록에서는 ProductListItemDataSource를 만들 때 카테고리 값이 null이어야 하므로 null을 넘겨주고 키워드를 함께 넘겨준다.
 */

class ProductSearchViewModel(app: Application) : BaseViewModel(app),
    ProductListPagedAdapter.ProductLiveDataBuilder,
    ProductListPagedAdapter.OnItemClickListener {

    var keyword: String? = null
    val products = buildPagedList()

    override fun createDataSource() = ProductListItemDataSource(null, keyword)

    override fun onClickProduct(productId: Long?) {
        startActivity<ProductDetailActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        }
    }
}