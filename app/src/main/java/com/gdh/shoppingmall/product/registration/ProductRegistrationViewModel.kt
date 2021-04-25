package com.gdh.shoppingmall.product.registration

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.gdh.shoppingmall.product.category.categoryList
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

/**
 * imagesUrls -> 이미지 업로드 후에 반환 받은 이미지 주소를 저장할 변수, 주소가 입력되면
 *               자동으로 이미지를 표시해주도록 데이터바인딩을 이용하기 위해서 List<MutableLiveData<String?>> 로 선언
 * imagesIds -> 업로드 후 반환받은 이미지의 id들을 저장할 리스트
 * categories -> 뷰에서 스피너로 보여줄 카테고리명 리스트를 담는 변수
 * categoryIdSelected -> 뷰의 스피너를 통해 선택된 카테고리의 아이디를 저장할 변수, 디폴트 값으로 첫 번째 카테고리 아이디 값 사용
 * ...length -> 뷰에 표시될 현재 각 텍스트의 길이(0/00) 형태, 데이터 바인딩 사용
 */

class ProductRegistrationViewModel(app: Application) : BaseViewModel(app){

    val imagesUrls: List<MutableLiveData<String?>> = listOf(
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?)
    )

    val imagesIds: MutableList<Long?> = mutableListOf(null, null, null, null)

    val productName = MutableLiveData("")
    val description = MutableLiveData("")
    val price = MutableLiveData("")
    val categories = MutableLiveData(categoryList.map { it.name })
    var categoryIdSelected: Int? = categoryList[0].id

    val descriptionLimit = 500
    val productNameLimit = 40

    val productNameLength = MutableLiveData("0/$productNameLimit")
    val descriptionLength = MutableLiveData("0/$descriptionLimit")

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
}