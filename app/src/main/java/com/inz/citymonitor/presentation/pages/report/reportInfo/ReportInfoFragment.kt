package com.inz.citymonitor.presentation.pages.report.reportInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class ReportInfoFragment : BaseFragment() {
    override fun setTopBarTitle() = "Szcegoły zlgoszenia "


    val viewModel by lazy { ReportInfoViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report_info, container, false)
    }
}
