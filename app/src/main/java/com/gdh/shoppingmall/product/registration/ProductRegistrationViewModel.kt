package com.gdh.shoppingmall.product.registration

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gdh.shoppingmall.api.request.ProductRegistrationRequest
import com.gdh.shoppingmall.api.response.ApiResponse
import com.gdh.shoppingmall.api.response.ProductImageUploadResponse
import com.gdh.shoppingmall.product.category.categoryList
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import retrofit2.Response

/**
 * imagesUrls -> 이미지 업로드 후에 반환 받은 이미지 주소를 저장할 변수, 주소가 입력되면
 *               자동으로 이미지를 표시해주도록 데이터바인딩을 이용하기 위해서 List<MutableLiveData<String?>> 로 선언
 * imagesIds -> 업로드 후 반환받은 이미지의 id들을 저장할 리스트
 * categories -> 뷰에서 스피너로 보여줄 카테고리명 리스트를 담는 변수
 * categoryIdSelected -> 뷰의 스피너를 통해 선택된 카테고리의 아이디를 저장할 변수, 디폴트 값으로 첫 번째 카테고리 아이디 값 사용
 * ...length -> 뷰에 표시될 현재 각 텍스트의 길이(0/00) 형태, 데이터 바인딩 사용
 *
 * 이미지 등록 로직 구현
 * Intent(Intent.ACTION_GET_CONTENT) -> 로컬 파일 시스템에서 특정 타입의 파일을 선택하는 액티비티 인텐트를 생성
 *
 * register() -> 뷰에서 버튼 클릭했을 때 실행될 함수, API 요청 객체를 만들고 API를 호출한 후 응답 결과를 처리하는 순서
 */

class ProductRegistrationViewModel(app: Application) : BaseViewModel(app){

    val imageUrls: List<MutableLiveData<String?>> = listOf(
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?)
    )

    val imageIds: MutableList<Long?> = mutableListOf(null, null, null, null)

    val productName = MutableLiveData("")
    val description = MutableLiveData("")
    val price = MutableLiveData("")
    val categories = MutableLiveData(categoryList.map { it.name })
    var categoryIdSelected: Int? = categoryList[0].id

    val descriptionLimit = 500
    val productNameLimit = 40

    val productNameLength = MutableLiveData("0/$productNameLimit")
    val descriptionLength = MutableLiveData("0/$descriptionLimit")

    var currentImageNum = 0

    fun checkProductNameLength() {
        productName.value?.let {
            if (it.length > productNameLimit) {
                productName.value = it.take(productNameLimit)
            }
            productNameLength.value = "${productName.value?.length}/$productNameLimit"
        }
    }

    fun checkDescriptionLength() {
        description.value?.let {
            if(it.length > descriptionLimit) {
                descriptionLength.value = it.take(descriptionLimit)
            }
            descriptionLength.value = "${description.value?.length}/$descriptionLimit"
        }
    }

    fun onCategorySelect(position: Int) {
        categoryIdSelected = categoryList[position].id
    }

    fun pickImage(imageNum: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

        intent.resolveActivity(app.packageManager)?.let {
            startActivityForResult(intent, REQUEST_PICK_IMAGES)
        }
        currentImageNum = imageNum
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) return

        when (resultCode) {
            REQUEST_PICK_IMAGES -> data?.let { uploadImage(it) }
        }
    }

    private fun uploadImage(intent: Intent) =
        getContent(intent.data)?.let { imageFile ->
            viewModelScope.launch {
                val response = ProductImageUploader().upload(imageFile)
                onImageUploadResponse(response)
            }
        }

    private fun onImageUploadResponse(response: ApiResponse<ProductImageUploadResponse>) {
        if (response.success && response.data != null) {
            imageUrls[currentImageNum].value = response.data.filePath
            imageIds[currentImageNum] = response.data.productImageId
        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    suspend fun register() {
        val request = extractRequest()
        val response = ProductRegistrar().register(request)
        onRegistrationResponse(response)
    }

    private fun extractRequest(): ProductRegistrationRequest =
        ProductRegistrationRequest(
            productName.value,
            description.value,
            price.value?.toIntOrNull(),
            categoryIdSelected,
            imageIds)

    private fun onRegistrationResponse(response: ApiResponse<Response<Void>>) {
        if (response.success) {
            confirm("상품이 등록되었습니다.") {
                finishActivity()
            }
        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    companion object {
        const val REQUEST_PICK_IMAGES = 0
    }
}