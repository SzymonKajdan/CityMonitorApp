package com.inz.citymonitor.presentation.customViews.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.inz.citymonitor.R
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import kotlinx.android.synthetic.main.item_menu.view.*
import javax.inject.Inject

class MenuAdapter(val drawerLayout: DrawerLayout, val navController: NavController) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    init {
        Injector.componet.inject(this)
    }

    @Inject
    lateinit var localStorage: LocalStorage

    private var items: List<MenuItem>? = null

    fun setData(items: List<MenuItem>) {

        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.itemView.apply {
            name.text = item?.name
            setOnClickListener {
                item?.destinationId.let {
                    if (item?.destinationId == null) {
                        localStorage.logOut()
                        navController.navigate(R.id.mapFragment)
                    }
                    else
                        navController.navigate(item.destinationId)
                }
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view)
}