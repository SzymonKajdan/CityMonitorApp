package com.inz.citymonitor.presentation.pages.history.historyDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class HistoryDetailsFragment : BaseFragment() {
    override fun setTopBarTitle()="Historia Zgloszenia "


    val viewModel by lazy{HistoryDetailsViewModel()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_details, container, false)
    }


}
