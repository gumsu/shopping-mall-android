package com.gdh.shoppingmall.product

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.gdh.shoppingmall.R
import com.gdh.shoppingmall.product.list.ProductListPagerAdapter
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class ProductMainActivity : BaseActivity<ProductMainViewModel>() {

    override val viewModelType = ProductMainViewModel::class
    private val ui by lazy { ProductMainUI(getViewModel()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)
        ui.viewPager.adapter = ProductListPagerAdapter(supportFragmentManager)
        ui.tablayout.setupWithViewPager(ui.viewPager)
        setupDrawerListener()
    }

    private fun setupDrawerListener() {
        val toggle = ActionBarDrawerToggle(
            this,
            ui.drawerLayout,
            ui.toolbar,
            R.string.open_drawer_description,
            R.string.close_drawer_description
        )
        ui.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
}