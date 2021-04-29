package com.gdh.shoppingmall.product.list

import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdh.shoppingmall.api.ApiGenerator
import com.gdh.shoppingmall.api.response.ProductListItemResponse
import com.gdh.shoppingmall.common.paging.LiveDataBuilder
import com.gdh.shoppingmall.product.ProductStatus
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat

/**
 * PagedListAdapter를 상속받아 ProductListPagedAdapter 작성
 *
 * DIFF_CALLBACK -> 동일한 데이터의 경우 뷰를 다시 그려주는 낭비를 피하기 위해 생성자에서 객체의 동일성을 검사하기 위한 콜백을 받는다.
 *                  아이템이 동일한지, 컨텐츠가 동일한지 여부를 id와 data class에 자동 생성되는 toString()함수로 검사.
 *
 * onCreateViewHolder() -> RecyclerView가 새 ViewHolder를 요구했을 때, 호출되는 콜백, 인스턴스를 생성하여 반환
 * onBindViewHolder() -> RecyclerView가 특정 위치의 데이터를 출력해주려 할 때, 호출되는 콜백, bind() 호출하여 반환
 * ProductItemViewHolder -> ProductListItemUI를 가지고 있어야 하는 ViewHolder 클래스
 *                          이렇게 넘겨준 뷰는 이후에 내부에서 itemView라는 프로퍼티로 사용 가능
 * bind() -> RecyclerView가 화면에 아이템을 표시해 줄 때, 어댑터의 onBindViewHolder() 콜백에서 호출되도록 만든 함수
 *          이미지, 상품명, 가격을 데이터의 값으로 변경
 */

class ProductListPagedAdapter(
    private val listener: OnItemClickListener
) : PagedListAdapter<ProductListItemResponse, ProductListPagedAdapter.ProductItemViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductItemViewHolder(parent, listener)

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductItemViewHolder(
        parent: ViewGroup,
        private val listener: OnItemClickListener,
        private val ui: ProductListItemUI = ProductListItemUI()
    ) : RecyclerView.ViewHolder(ui.createView(AnkoContext.create(parent.context, parent))) {

        var productId: Long? = null

        init {
            itemView.onClick { listener.onClickProduct(productId) }
        }

        fun bind(item: ProductListItemResponse?) = item?.let {
            this.productId = item.id
            val soldOutString = if(ProductStatus.SOLD_OUT == item.status) "(품절)" else ""
            val commaSeparatedPrice = NumberFormat.getNumberInstance().format(item.price)

            ui.productName.text = item.name
            ui.price.text = "\$commaSeparatedPrice $soldOutString"

            Glide.with(ui.imageView)
                .load("${ApiGenerator.HOST}${item.imagePaths.firstOrNull()}")
                .centerCrop()
                .into(ui.imageView)
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ProductListItemResponse>() {
                override fun areItemsTheSame(
                    oldItem: ProductListItemResponse,
                    newItem: ProductListItemResponse
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: ProductListItemResponse,
                    newItem: ProductListItemResponse
                ) = oldItem.toString() == newItem.toString()
            }
    }

    interface OnItemClickListener {
        fun onClickProduct(productId: Long?)
    }

    interface ProductLiveDataBuilder : LiveDataBuilder<Long, ProductListItemResponse>
}