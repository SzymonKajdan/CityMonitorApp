package com.inz.citymonitor.presentation.pages.signUp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment

class SingUpFragment :BaseFragment() {
    override fun setTopBarTitle() {
        topBar?.setTopBarTitle("Zarejstruj")
    }


    val viewModel by lazy { SingUpViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

}
