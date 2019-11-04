package com.inz.citymonitor.presentation.base

import android.content.Context
import android.util.Log
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.inz.citymonitor.presentation.activities.MainActivityInterface
import com.inz.citymonitor.presentation.activities.MainInterface
import com.inz.citymonitor.presentation.customViews.TopBar
import java.lang.Exception

abstract class BaseFragment : Fragment() {
    var topBar: TopBar? = null

    var drawerLayout: DrawerLayout? = null

    var actions: MainActivityInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actions = context as? MainInterface
        } catch (e: Exception) {
            throw IllegalStateException("Main fragment must implement correct action interface")
        }
        topBar = actions?.getTopBar()
        drawerLayout = actions?.getDrawerLayout()
    }
    abstract  fun setTopBarTitle()


    override fun onDetach() {
        super.onDetach()
        topBar = null
        drawerLayout = null
    }
}