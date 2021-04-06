package com.gdh.shoppingmall.product

import android.view.Gravity
import android.view.View
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.support.v4.drawerLayout

/**
 * DrawerLayout을 화면의 최상단 레이아웃으로 사용
 *
 * verticalLayout -> LinearLayout의 vertical
 *
 * NavigationView() -> 네비게이션 드로어 생성
 *
 * lparams > gravity 해당 뷰가 컨테이너보다 작은 경우 컨테이너의 어느 쪽에 정렬할 것인가?
 * 좌측 정렬이므로 START
 */

class ProductMainUI(
    private val viewModel: ProductMainViewModel
) : AnkoComponent<ProductMainActivity> {

    lateinit var navigationView: NavigationView

    override fun createView(ui: AnkoContext<ProductMainActivity>) =
        ui.drawerLayout {
            verticalLayout {
            }.lparams(matchParent, matchParent)

            navigationView = navigationView {
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.START
            }
        }
}