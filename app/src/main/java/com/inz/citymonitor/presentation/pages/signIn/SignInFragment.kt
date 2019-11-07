package com.inz.citymonitor.presentation.pages.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class SignInFragment : BaseFragment() {

    val viewModel by lazy { SignInFragment() }

    override fun setTopBarTitle()="Zaloguj"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }


}
