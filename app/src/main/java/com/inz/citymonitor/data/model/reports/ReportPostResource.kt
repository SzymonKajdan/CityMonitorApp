package com.inz.citymonitor.data.model.reports

import com.inz.citymonitor.data.model.ReportsType

data class ReportPostResource(
    var longitude: String?,
    var latitude: String?,
    var description: String,
    var reportType:String,
    var photo: String?,
    var video: String?,
    var cityName: String
)