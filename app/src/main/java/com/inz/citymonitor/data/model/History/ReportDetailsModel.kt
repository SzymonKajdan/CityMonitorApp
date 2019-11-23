package com.inz.citymonitor.data.model.History

data class ReportDetailsModel(
    var id: Long,
    var longitude: String,
    var latitude: String,
    var description: String,
    var photo: String?,
    var isActive: Boolean,
    var reportType: String,
    var reportDate: String,
    var mark: Double,
    var userId: Long,
    var video: String?
)