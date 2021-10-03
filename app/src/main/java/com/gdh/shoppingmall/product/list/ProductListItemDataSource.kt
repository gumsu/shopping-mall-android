package com.gdh.shoppingmall.product.list

import androidx.paging.PageKeyedDataSource
import com.gdh.shoppingmall.App
import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.api.response.ApiResponse
import com.gdh.shoppingmall.api.response.ProductListItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.toast

/**
 * PagedListAdapter에 쓰이는 초기 데이터, 다음 데이터, 이전 데이터를 가져오도록 세 가지 콜백을 정의
 * PageKeyedDataSource를 상속 받는 클래스, 이 클래스는 초기 데이터를 로드하고 보다 이전/이후의 데이터를 로드하기 위한 콜백으로 구성
 *
 * loadInitial() -> 초기 데이터를 로드하는 콜백, 상품을 최신순으로 읽어와야 하기 때문에 id로는 Long.MAX_VALUE를 사용
 * API로부터 데이터를 받았다면 callback.onResult()를 호출해 데이터가 추가되었음을 알려야 한다.
 * loadInitial()의 파라미터인 LoadInitialCallback의 onResult()는 두 번째와 세 번째 파라미터로
 * API로부터 받은 첫 번째 데이터의 id와 마지막 데이터의 id를 넣어주게 되어있다.
 *
 * 콜백은 UI 쓰레드에서 실행되지 않기 때문에 토스트 메시지를 보여주기 위해서는 메인 쓰레드로 변경 후 호출해야 한다.
 *
 * loadBefore() -> 이전(더 나중에 등록된) 데이터를 불러오기 위해 사용, onResult()에 API로부터 받은 첫 번째 데이터 id 넘겨준다.
 *
 * loadAfter() -> 다음(과거) 데이터를 불러오기 위해 사용, onResult()에 API로부터 받은 마지막 데이터 id 넘겨준다.
 *
 * 검색 결과는 상품 리스트와 동일한 형태로 출력되기 때문에 상품 리스트 화면에 쓰인 요소들을 약간 수정하여 구현한다.
 */

class ProductListItemDataSource(
    private val categoryId: Int?,
    private val keyword: String? = null
) : PageKeyedDataSource<Long, ProductListItemResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(Long.MAX_VALUE, NEXT)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id, it.last().id)
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(params.key, PREV)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id)
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(params.key, NEXT)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.last().id)
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    private fun getProducts(id: Long, direction: String) = runBlocking {
        try {
            ParayoApi.instance.getProducts(id, categoryId, direction, keyword)
        } catch (e: Exception) {
            ApiResponse.error<List<ProductListItemResponse>>("알 수 없는 오류가 발생했습니다.")
        }
    }

    private fun showErrorMessage(response: ApiResponse<List<ProductListItemResponse>>) {
        App.instance.toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
    }

    companion object {
        private const val NEXT = "next"
        private const val PREV = "prev"
    }
}