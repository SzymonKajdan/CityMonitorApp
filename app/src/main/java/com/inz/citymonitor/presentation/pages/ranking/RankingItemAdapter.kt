package com.inz.citymonitor.presentation.pages.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ranking.RankingModel
import com.inz.citymonitor.presentation.pages.history.HistoryFragmentDirections
import kotlinx.android.synthetic.main.history_item.view.*
import kotlinx.android.synthetic.main.item_ranking.view.*

class RankingItemAdapter : RecyclerView.Adapter<RankingItemAdapter.ViewHolder>() {

    private var items: List<RankingModel>? = null

    fun setData(items: List<RankingModel>) {

        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.itemView.apply {
            pos.text=(position+1).toString()
            userName.text=item?.username
            avgMark.text=item?.avgMark.toString()
        }
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view)
}