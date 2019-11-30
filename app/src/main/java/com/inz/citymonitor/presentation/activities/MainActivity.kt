package com.inz.citymonitor.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.customViews.TopBar
import com.inz.citymonitor.presentation.customViews.navigation.MenuAdapter
import com.inz.citymonitor.presentation.pages.map.MapViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainInterface {


    private val viewModel by lazy { MainViewModel() }
    private lateinit var adapter: MenuAdapter

    override var actions: MainActivityInterface?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun getDrawerLayout(): DrawerLayout? {
        return drawerLayout
    }

    override fun getTopBar(): TopBar? {
        return topBar
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        topBar.setTopBarListener(object:TopBar.TopBarListener{
//            override fun onLeftButtonClick() {
//                toogleDrawer()
//
//            }
//        })

        adapter = MenuAdapter(
            drawerLayout = drawerLayout,
            navController = this.findNavController(R.id.nav_host_fragment)
        )

        viewModel.localStorage.isLogged().observe(this, Observer {
            adapter.setData(viewModel.getItemList(it))
        })


        navigationDrawer.setAdapter(adapter)
        topBar.onLeftButtonClick = ::toogleDrawer



    }

    fun toogleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START, true)
        } else {
            drawerLayout.openDrawer(GravityCompat.START, true)
        }
    }
}
