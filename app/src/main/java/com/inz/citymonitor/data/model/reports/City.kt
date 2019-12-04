package com.inz.citymonitor.data.model.reports

data class City(
    var id: Long,
    var cityName: String,
    var longitude: String,
    var latitude: String,
    var reports: List<Reports>
);