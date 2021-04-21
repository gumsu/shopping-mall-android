package com.gdh.shoppingmall.product

import android.graphics.Typeface
import android.view.View
import com.gdh.shoppingmall.R
import com.gdh.shoppingmall.common.Prefs
import com.gdh.shoppingmall.view.borderBottom
import org.jetbrains.anko.*

/**
 * userName -> SharedPreference에 저장한 userName 값
 */

class ProductMainNavHeader : AnkoComponent<View> {

    override fun createView(ui: AnkoContext<View>): View  =
        ui.verticalLayout {
            padding = dip(20)
            background = borderBottom(width =  dip(1))

            imageView(R.drawable.ic_logo_black)
                .lparams(dip(54), dip(54))

            textView(Prefs.userName) {
                topPadding = dip(8)
                textSize = 20f
                typeface = Typeface.DEFAULT_BOLD
            }
        }
}