package com.inz.citymonitor.presentation.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.inz.citymonitor.R

class NavigationDrawer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.navigation_drawer,this)
    }
}