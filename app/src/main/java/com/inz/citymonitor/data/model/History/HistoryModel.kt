package com.inz.citymonitor.data.model.History

import java.util.*

data class HistoryModelLight(
    var id: Long,
    var longitude: String,
    var latitude: String,
    var description: String,
    var photo: String?,
    var isActive: Boolean,
    var reportType: String,
    var reportDate:String
)