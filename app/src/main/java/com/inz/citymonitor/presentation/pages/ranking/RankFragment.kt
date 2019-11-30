package com.inz.citymonitor.presentation.pages.ranking

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.ranking.RankingModel
import com.inz.citymonitor.presentation.base.BaseFragment
import com.inz.citymonitor.presentation.pages.history.HistoryItemAdapter
import kotlinx.android.synthetic.main.fragment_history.*

class RankFragment : BaseFragment() {
    override fun setTopBarTitle() = "Ranking"


    private val adapter by lazy { RankingItemAdapter() }
    val viewModel by lazy { RankViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RankFragment.adapter
        }
        viewModel.getRankig()

        viewModel.callResult.observe(viewLifecycleOwner, Observer {
            when (it) {
             is ErrorResponseModel ->{
                 Toast.makeText(context,it.code+" "+it.details,Toast.LENGTH_SHORT).show()
             }
                is ArrayList<*>->{

                    adapter.setData(it as List<RankingModel>)

                }

            }
        })
    }
}
