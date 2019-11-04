package com.inz.citymonitor.presentation.pages.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : BaseFragment() {
    override fun setTopBarTitle() {
        topBar?.setTopBarTitle("MapFragment")
    }

    private val viewModel by lazy { MapViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singUpButton.setOnClickListener { findNavController().navigate(R.id.singUpFragment) }

    }


}
