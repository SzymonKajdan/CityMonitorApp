package com.inz.citymonitor.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.customViews.TopBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MainInterface {
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
        topBar.onLeftButtonClick=::toogleDrawer

    }
    fun toogleDrawer(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START,true)
        }else{
            drawerLayout.openDrawer(GravityCompat.START,true)
        }
    }
}
