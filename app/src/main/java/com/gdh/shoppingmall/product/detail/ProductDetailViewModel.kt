package com.gdh.shoppingmall.product.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.api.response.ApiResponse
import com.gdh.shoppingmall.api.response.ProductResponse
import com.gdh.shoppingmall.product.ProductStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.databinding.addAll
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error
import java.text.NumberFormat

/**
 * loadDetail() -> 상품 정보를 가져오기 위한 함수. suspend 함수인 API를 호출하는 부분이 포함되므로 코루틴 사용
 * updateViewData() -> 상품 정보를 가져온 후 그 정보들을 뷰에 보여주기 위해 수정하는 함수
 */

class ProductDetailViewModel(app: Application) : BaseViewModel(app) {

    var productId: Long? = null

    val productName = MutableLiveData("-")
    val description = MutableLiveData("")
    val price = MutableLiveData("-")
    val imageUrls: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    fun loadDetail(id: Long) = viewModelScope.launch(Dispatchers.Main) {
        try {
            val response = getProduct(id)
            if (response.success && response.data != null) {
                updateViewData(response.data)
            } else {
                toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            toast(e.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    private suspend fun getProduct(id: Long) = try {
        ParayoApi.instance.getProducts(id)
    } catch (e: Exception) {
        error("상품 정보를 가져오는 중 오류 발생", e)
        ApiResponse.error<ProductResponse>("상품 정보를 가져오는 중 오류가 발생했습니다.")
    }

    private fun updateViewData(product: ProductResponse) {
        val commaSeparatedPrice = NumberFormat.getInstance().format(product.price)
        val soldOutString = if (ProductStatus.SOLD_OUT == product.status) "(품절)" else ""

        productName.value = product.name
        description.value = product.description
        price.value = "₩${commaSeparatedPrice} $soldOutString"
        imageUrls.addAll(product.imagePaths)
    }

    fun openInquiryActivity() {
        toast("상품 문의 - productId = $productId")
    }
}