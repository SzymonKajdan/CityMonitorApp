package com.inz.citymonitor.presentation.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.inz.citymonitor.R
import kotlinx.android.synthetic.main.top_bar.view.*

class TopBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onLeftButtonClick: () -> Unit = {}

    var onRightButtonClick:()->Unit={}

    private lateinit var topBarListener: TopBarListener

    fun setTopBarListener(topBarListener: TopBarListener) {
        this.topBarListener = topBarListener

    }

    init {
        View.inflate(context, R.layout.top_bar, this)
        left_button.setOnClickListener {
            onLeftButtonClick()
//        topBarListener.onLeftButtonClick()
        }
        right_button.setOnClickListener {
            onRightButtonClick()
        }


    }

    fun setTopBarTitle(title: String?) {
        this.title.text = title
    }

    interface TopBarListener {
        fun onLeftButtonClick()
    }
}