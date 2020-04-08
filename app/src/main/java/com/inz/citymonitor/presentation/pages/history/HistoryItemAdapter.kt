package com.inz.citymonitor.presentation.pages.history

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.History.HistoryModelLight
import kotlinx.android.synthetic.main.history_item.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.time.format.DateTimeFormatter

class HistoryItemAdapter : RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>() {


    private var items: List<HistoryModelLight>? = null
    private var filterdItems: List<HistoryModelLight>? = null

    private var desc: Boolean = true

    fun setData(items: List<HistoryModelLight>) {

        this.items = items
        this.filterdItems = items
        notifyDataSetChanged()
    }

    fun filterData(type: String) {
        this.filterdItems = items?.filter { it.reportType == type }
        notifyDataSetChanged()
    }

    fun sortByDate() {
        if (desc) {
            this.filterdItems = filterdItems?.sortedBy {
                DateTime.parse(it.reportDate,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"))
            }
            desc = false
        } else {
            this.filterdItems = filterdItems?.sortedByDescending {
                DateTime.parse(it.reportDate,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"))
            }
            desc = true
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterdItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filterdItems?.get(position)
        holder.itemView.apply {
            if (item?.isActive == true) rate.setBackgroundResource(R.color.active) else rate.setBackgroundResource(
                R.color.noactive
            )
            type.text = item?.reportType
            if (item?.photo != null)
                Glide.with(context)
                    .load(item.photo)
                    .centerCrop()
                    .into(photo)

            setOnClickListener {
                if (item != null) {

                    findNavController().navigate(
                        HistoryFragmentDirections.toHistoryDetailsFragment(
                            item.id
                        )
                    )

                }
            }
        }
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view)
}