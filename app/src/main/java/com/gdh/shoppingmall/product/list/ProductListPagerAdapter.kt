package com.gdh.shoppingmall.product.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.gdh.shoppingmall.product.category.categoryList

/**
 * 뷰페이저에서 프래그먼트를 보여줄 수 있도록 로직 작성
 *
 * BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT -> 현재 프래그먼트만 라이프 사이클의 RESUMED 상태에 있을 수 있다는 것을 의미
 *                                          나머지 프래그먼트들은 STARTED 상태만 가진다.
 * fragments -> 뷰페이저에서 보여줄 프래그먼트 리스트들 카테고리 리스트와 1:1이 되도록 카테고리만큼 생성하여 id와 title을 부여
 *
 * getItem() -> 지정된 위치의 Fragment를 반환하는 함수, FragmentStatePagerAdapter에 정의된 추상 함수로 구현 필수
 * getCount() -> 프래그먼트 개수 반환
 * getPageTitle() -> 프래그먼트의 title 반환
 */

class ProductListPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(
    fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val fragments = categoryList.map {
        ProductListFragment.newInstance(it.id, it.name)
    }

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int) = getItem(position).title
}