package com.gdh.shoppingmall.product

import android.view.Gravity
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.view.View
import com.gdh.shoppingmall.R
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
 *
 * toolbar -> title 툴바 타이틀
 *            menu.add 툴바의 우측 메뉴를 추가
 *            menu.add 로 추가된 메뉴는 보통 팝업 메뉴 안에 나타난다.
 *            툴바에 직접 표시하기 위해서 setShowAsAction(SHOW_AS_ACTION_ALWAYS) 사용
 */

class ProductMainUI(
    private val viewModel: ProductMainViewModel
) : AnkoComponent<ProductMainActivity> {

    lateinit var navigationView: NavigationView

    override fun createView(ui: AnkoContext<ProductMainActivity>) =
        ui.drawerLayout {
            verticalLayout {
                toolbar {
                    title = "Parayo"
                    menu.add("Search")
                        .setIcon(R.drawable.ic_search_black)
                        .setShowAsAction(SHOW_AS_ACTION_ALWAYS)
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, matchParent)

            navigationView = navigationView {
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.START
            }
        }
}