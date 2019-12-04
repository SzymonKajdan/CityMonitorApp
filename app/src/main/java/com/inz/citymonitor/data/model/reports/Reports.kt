package com.inz.citymonitor.data.model.reports

import java.util.*

data class Reports(
    var id: Long,
    var longitude: String,
    var latitude: String,
    var description: String,
    var photo: String,
    var reportType: String,
    var isActive: Boolean,
    var reportDate: String
)