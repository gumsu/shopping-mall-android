package com.gdh.shoppingmall.product.list

import android.app.Application
import android.content.Intent
import androidx.paging.DataSource
import com.gdh.shoppingmall.api.response.ProductListItemResponse
import com.gdh.shoppingmall.product.detail.ProductDetailActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error

/**
 * RecyclerView에 ProductListPagedAdapter를 바인딩 해주기 위해 프로퍼티 추가
 *
 * categoryId: Int = -1 -> 각 프래그먼트에서 표시되어야 할 아이템 카테고리 id, -1인 경우 오류 로그 기록
 * products = buildPagedList() -> 데이터 소스로부터 아이템 리스트를 어떻게 가져올 것인지에 대한 설정, 페이지 사이즈는 10
 *
 * createDataSource() -> ProductListPagedAdapter.ProductLiveDataBuilder를 구현했을 때 작성해야 하는 함수
 *                       상품 목록에 필요한 ProductListItemDataSource를 반환
 * onClickProduct() -> 상품 목록에서 상품을 클릭했을 때 동작할 리스너 구현
 */

class ProductListViewModel(app: Application) : BaseViewModel(app),
    ProductListPagedAdapter.ProductLiveDataBuilder, ProductListPagedAdapter.OnItemClickListener {

    var categoryId: Int = -1
    val products = buildPagedList()

    override fun createDataSource(): DataSource<Long, ProductListItemResponse> {
        if(categoryId == -1)
            error("categoryId가 설정되지 않았습니다", IllegalStateException("categoryId is -1"))
        return ProductListItemDataSource(categoryId)
    }

    override fun onClickProduct(productId: Long?) {
        startActivity<ProductDetailActivity>() {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        }
    }

}