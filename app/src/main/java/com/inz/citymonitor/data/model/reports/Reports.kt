package com.inz.citymonitor.data.model.reports

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable
import java.util.*

class Reports : ClusterItem,Serializable {
    override fun getPosition(): LatLng {
        return LatLng(this.latitude.toDouble(), this.longitude.toDouble())
    }

    override fun getTitle(): String {
        return ""
    }

    override fun getSnippet(): String {
        return ""
    }

    var id: Long = 0
    var longitude: String = ""
    var latitude: String = ""
    var description: String = ""
    var photo: String = ""
    var reportType: String = " "
    var isActive: Boolean = true
    var reportDate: String = " "


}