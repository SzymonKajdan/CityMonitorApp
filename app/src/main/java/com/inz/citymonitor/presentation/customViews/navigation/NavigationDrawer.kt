package com.inz.citymonitor.presentation.customViews.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.inz.citymonitor.R
import kotlinx.android.synthetic.main.navigation_drawer.view.*

class NavigationDrawer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.navigation_drawer,this)
    }

    fun setAdapter(adapter: MenuAdapter){
        recycleView.apply {
            layoutManager=LinearLayoutManager(context)
            this.adapter=adapter

        }

    }
}