package com.gdh.shoppingmall.common.paging

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

/**
 * PageKeyedDataSource를 이용해 LiveData<PagedList>를 쉽게 만들 수 있도록 정의한 인터페이스
 *
 * createDataSource() -> 실제로 구현해주어야 할 함수, 구현체에서 필요한 DataSource 클래스를 반환해주는 함수
 * factory() -> DataSource를 생성해줄 DataSource.Factory 객체를 만드는 함수
 * config() -> 한 페이지를 어떻게 가져올 것인가? 설정해주는 함수
 * buildPagedList() -> LiveData<PagedList> 생성해주는 함수
 */

interface LiveDataBuilder<K, T> {

    fun createDataSource(): DataSource<K, T>

    private fun factory() = object : DataSource.Factory<K, T>() {
        override fun create(): DataSource<K, T> = createDataSource()
    }

    private fun config() = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    fun buildPagedList() = LivePagedListBuilder(factory(), config()).build()
}