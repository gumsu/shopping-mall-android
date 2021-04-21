package com.gdh.shoppingmall.product

import android.view.Gravity
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.gdh.shoppingmall.R
import com.gdh.shoppingmall.view.borderBottom
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
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
 *
 *            햄버거 버튼을 달고 네비게이션 드로어와 연결하기 위해 toolbar 변수를 만들고
 *            툴바 UI 코드에서 변수에 객체를 대입해준다.(toolbar를 activity에 접근할 수 있도록 변수 생성)
 */

class ProductMainUI(
    private val viewModel: ProductMainViewModel
) : AnkoComponent<ProductMainActivity> {

    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout

    override fun createView(ui: AnkoContext<ProductMainActivity>) =
        ui.drawerLayout {
            drawerLayout = this

            verticalLayout {
                toolbar = toolbar {
                    title = "Parayo"
                    bottomPadding = dip(1)
                    background = borderBottom(width = dip(1))
                    menu.add("Search")
                        .setIcon(R.drawable.ic_search_black)
                        .setShowAsAction(SHOW_AS_ACTION_ALWAYS)
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, matchParent)

            navigationView = navigationView {
                ProductMainNavHeader()
                    .createView(AnkoContext.create(context, this))
                    .run(::addHeaderView)
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.START
            }
        }
}