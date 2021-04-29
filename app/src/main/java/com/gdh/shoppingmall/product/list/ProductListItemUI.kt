package com.gdh.shoppingmall.product.list

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import com.gdh.shoppingmall.R
import com.gdh.shoppingmall.view.borderBottom
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout

class ProductListItemUI : AnkoComponent<ViewGroup> {

    // 이미지, 상품명, 가격을 변경할 수 있도록 별개의 프로퍼티 선언
    lateinit var imageView: ImageView
    lateinit var productName: TextView
    lateinit var price: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) =
        ui.constraintLayout { 
            topPadding = dip(20)
            bottomPadding = dip(20)
            clipToOutline = false
            background = borderBottom(width = 1)
            lparams(matchParent, wrapContent)
            
            imageView = imageView { 
                id = View.generateViewId()  // constraintLayout을 위한 id 지정
                scaleType = ImageView.ScaleType.CENTER_CROP
            }.lparams(dip(80), dip(80))
            
            productName = textView("-") {
                id = View.generateViewId()
                textSize = 16f
                typeface = Typeface.DEFAULT_BOLD
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
            }.lparams(0, wrapContent)
            
            price = textView("-") {
                id = View.generateViewId()
                textColorResource = R.color.design_default_color_primary
                textSize = 14f
            }
            
            applyConstraintSet { 
                imageView.id {
                    connect(
                        TOP to TOP of PARENT_ID, 
                        START to START of PARENT_ID margin dip(20),
                        BOTTOM to BOTTOM of PARENT_ID
                    )
                }
                
                productName.id {
                    connect(
                        TOP to TOP of imageView.id margin dip(4),
                        END to END of PARENT_ID margin dip(20),
                        START to END of imageView.id margin dip(10)
                    )
                }
                
                price.id {
                    connect(
                        TOP to BOTTOM of productName.id margin dip(4),
                        START to END of imageView.id margin dip(10)
                    )
                }
            }
        }
}