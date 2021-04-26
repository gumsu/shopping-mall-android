package com.gdh.shoppingmall.product.registration

import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.api.response.ApiResponse
import com.gdh.shoppingmall.api.response.ProductImageUploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.io.File

/**
 * upload() -> 파일 객체를 받아 API 요청에 맞는 파라미터를 생성하고 업로드 API를 호출하는 함수
 *             네트워크 요청이 일어나는 곳이기 때문에 IO 쓰레드에서 수행되도록 해야 한다.
 * MediaType.parse() -> HTTP 요청이나 바디에 사용될 컨텐츠 타입을 지정하는 MediaType 객체 생성
 * RequestBody.create() -> HTTP 요청의 바디 생성, 바디는 MediaType과 파일로 이루어져 있다.
 * MultipartBody.Part.createFormData() -> 파라미터들을 이용해 멀티파트 요청의 바디를 생성
 */

class ProductImageUploader : AnkoLogger {

    suspend fun upload(imageFile: File) = try {
        val part = makeImagePart(imageFile)

        withContext(Dispatchers.IO) {
            ParayoApi.instance.uploadProductImages(part)
        }
    } catch (e: Exception) {
        error("상품 이미지 등록 오류", e)
        ApiResponse.error<ProductImageUploadResponse>("알 수 없는 오류가 발생했습니다.")
    }

    private fun makeImagePart(imageFile: File): MultipartBody.Part {
        val mediaType = MediaType.parse("multipart/form-data")
        val body = RequestBody.create(mediaType, imageFile)

        return MultipartBody.Part.createFormData("image", imageFile.name, body)
    }
}