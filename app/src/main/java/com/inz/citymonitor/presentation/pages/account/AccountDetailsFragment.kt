package com.inz.citymonitor.presentation.pages.account

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class AccountDetailsFragment : BaseFragment() {
    override fun setTopBarTitle()="Infomacje o Koncie"

    val viewModel by lazy{ AccountDetailsViewModel()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_details, container, false)
    }

}
