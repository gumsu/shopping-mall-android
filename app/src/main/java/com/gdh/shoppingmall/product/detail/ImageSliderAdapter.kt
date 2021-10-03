package com.gdh.shoppingmall.product.detail

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.gdh.shoppingmall.api.ApiGenerator
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.imageView

/**
 * 상세화면 이미지 슬라이더 어댑터
 *
 * isViewFromObject() -> 뷰페이지에서 현재 위치한 페이지가 instantiateItem 로부터 반환된 뷰인지 비교
 * instantiateItem() -> 실질적으로 우리가 원하는 뷰를 만들어 반환하는 함수
 * updateItems() -> 빈 리스트를 API로부터 받아온 이미지로 교체해주기 위해서 마련한 함수
 * destroyItem() -> 뷰페이저는 현재 페이지와 좌우로 이웃한 페이지만 생성하고 나머지 페이지는 삭제하기 때문에
 *                  이 때 뷰를 제거해주기 위해 만든 함수
 */

class ImageSliderAdapter : PagerAdapter() {

    var imageUrls: List<String> = listOf()

    override fun getCount() = imageUrls.size

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = AnkoContext.create(container.context, container)
            .imageView().apply {
                Glide.with(this).load("${ApiGenerator.HOST}${imageUrls[position]}").into(this)
            }
        container.addView(view)
        return view
    }

    fun updateItems(items: MutableList<String>) {
        imageUrls = items
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.invalidate()
    }
}