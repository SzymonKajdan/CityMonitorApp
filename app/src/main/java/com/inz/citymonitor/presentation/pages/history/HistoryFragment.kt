package com.inz.citymonitor.presentation.pages.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.History.HistoryModelLight
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : BaseFragment() {
    override fun setTopBarTitle() = "Twoja Hisotria"

    private val adapter by lazy { HistoryItemAdapter() }
    val viewModel by lazy { HistoryViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HistoryFragment.adapter
        }
        viewModel.callResult.observe(viewLifecycleOwner, Observer {
            adapter.setData(it as List<HistoryModelLight>)
        })
        viewModel.getReports()
    }

}
