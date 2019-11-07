package com.inz.citymonitor.presentation.pages.history

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class HistoryFragment : BaseFragment() {
    override fun setTopBarTitle()="Twoja Hisotria"


    val viewModel by lazy{ HistoryViewModel()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


}