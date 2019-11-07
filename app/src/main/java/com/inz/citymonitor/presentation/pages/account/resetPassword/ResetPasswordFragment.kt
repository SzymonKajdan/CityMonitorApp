package com.inz.citymonitor.presentation.pages.account.resetPassword

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class ResetPasswordFragment : BaseFragment() {
    override fun setTopBarTitle()= "resetuj haslo "


    val viewModel by lazy{ResetPasswordViewModel()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmnet_rest_password, container, false)
    }


}
