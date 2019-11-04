package com.inz.citymonitor.presentation.activities

import androidx.drawerlayout.widget.DrawerLayout
import com.inz.citymonitor.presentation.customViews.TopBar

interface MainActivityInterface {
    fun getTopBar(): TopBar?
    fun getDrawerLayout(): DrawerLayout?
}