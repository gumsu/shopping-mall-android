package com.gdh.shoppingmall.product

import android.view.Gravity
import android.view.Menu.NONE
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.gdh.shoppingmall.R
import com.gdh.shoppingmall.common.Prefs
import com.gdh.shoppingmall.signin.SignInActivity
import com.gdh.shoppingmall.view.borderBottom
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.support.v4.drawerLayout

/**
 * NavigationView.OnNavigationItemSelectedListener ->  네비게이션 메뉴 클릭시
 *                                                     호출될 함수를 정의한 인터페이스 상속, 로직 정의
 *
 * NavigationView -> 메뉴 클릭시 네비게이션 드로어를 닫기 위해 객체를 참조할 필요가 있음,
 *                   ProductMainUI 의 프로퍼티로 lateinit var 추가 선언
 *                   { ... } 블록을 초기화 해줄 때 앞서 선언한 navigationView 프로퍼티에 객체를 대입
 *
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
) : AnkoComponent<ProductMainActivity>, NavigationView.OnNavigationItemSelectedListener {

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

                menu.apply {
                    add(NONE, MENU_ID_INQUIRY, NONE, "내 문의").apply {
                        setIcon(R.drawable.ic_chat_black)
                    }
                    add(NONE, MENU_ID_LOGOUT, NONE, "로그아웃").apply {
                        setIcon(R.drawable.ic_siginout)
                    }
                }

                setNavigationItemSelectedListener(this@ProductMainUI) // this -> NavigationView 객체를 의미
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.START
            }
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_ID_INQUIRY -> { viewModel.toast("내 문의") }
            MENU_ID_LOGOUT -> {
                Prefs.token = null
                Prefs.refreshToken = null
                viewModel.startActivityAndFinish<SignInActivity>()
            }
        }

        drawerLayout.closeDrawer(navigationView) // 없으면 다시 돌아왔을 때 여전히 열려 있음

        return true
    }

    companion object {
        private const val MENU_ID_INQUIRY = 1
        private const val MENU_ID_LOGOUT = 2
    }
}