package com.inz.citymonitor.presentation.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.activities.MainActivityInterface
import com.inz.citymonitor.presentation.activities.MainInterface
import com.inz.citymonitor.presentation.customViews.TopBar
import java.lang.Exception

abstract class BaseFragment : Fragment() {
    var topBar: TopBar? = null

    var drawerLayout: DrawerLayout? = null

    var actions: MainActivityInterface? = null

    abstract  fun setTopBarTitle(): String?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topBar?.setTopBarTitle(setTopBarTitle())
        view.setPadding(0,context?.resources?.getDimensionPixelSize(R.dimen.topBarHeight)?:0,0,0)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actions = context as? MainActivityInterface
        } catch (e: Exception) {
            throw IllegalStateException("Main fragment must implement correct action interface")
        }
        topBar = actions?.getTopBar()
        drawerLayout = actions?.getDrawerLayout()
    }



    override fun onDetach() {
        super.onDetach()
        topBar = null
        drawerLayout = null
    }
}