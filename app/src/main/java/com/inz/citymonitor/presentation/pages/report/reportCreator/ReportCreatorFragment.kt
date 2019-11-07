package com.inz.citymonitor.presentation.pages.report.reportCreator

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class ReportCreatorFragment : BaseFragment() {
    override fun setTopBarTitle()="Nowe zgloszenie "



    val  viewModel by lazy{ReportCreatorViewModel()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report_creator, container, false)
    }


}
