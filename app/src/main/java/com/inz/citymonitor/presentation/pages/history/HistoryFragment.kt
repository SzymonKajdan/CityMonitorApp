package com.inz.citymonitor.presentation.pages.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.History.HistoryModelLight
import com.inz.citymonitor.data.model.ReportsType
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*
import java.util.ArrayList

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

        sortByDate.setOnClickListener {
            adapter.sortByDate()
        }

        filterByType.setOnClickListener {
            MaterialDialog(activity ?: return@setOnClickListener).show {
                var selected: String? = null
                title(text = "Filtrowanie po typie ")
                listItemsSingleChoice(items = ReportsType.values().map { it.type }) { dialog, index, text ->

                    adapter.filterData(text.toString())

                }
                positiveButton(text = "Filtruj")
            }
        }

        viewModel.callResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ErrorResponseModel -> {
                    Toast.makeText(context, "Try again later ", Toast.LENGTH_LONG).show()

                }
                is ArrayList<*> -> {
                    adapter.setData(it as List<HistoryModelLight>)
                }
            }
        })
        viewModel.getReports()
    }

}
