package com.gdh.shoppingmall.intro

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import com.gdh.shoppingmall.R
import org.jetbrains.anko.*

class IntroActivityUI : AnkoComponent<IntroActivity> {
    override fun createView(ui: AnkoContext<IntroActivity>): View {
        return ui.relativeLayout {
            gravity = Gravity.CENTER

            // PARAYO 텍스트 출력
            textView("PARAYO"){
                textSize = 24f
                textColorResource = R.color.design_default_color_primary
                typeface = Typeface.DEFAULT_BOLD
            }
        }
    }
}