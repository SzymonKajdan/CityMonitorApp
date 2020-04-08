package com.inz.citymonitor.data.model.User


data class SignInUserResponse(
    var token: String? = null,
    var user: User? = null
)


data class User(

    var id: Long? = null,
    var username: String? = null,
    var firstname: String? = null,
    var surname: String? = null,
    var email: String? = null,
    var isBanned: Boolean? = null,
    var userRating: Double? = null,
    var reports: List<Reports>? = null
)

data class Reports(
    var id: Long? = null,
    var longitude: String? = null,
    var latitude: String? = null,
    var description: String? = null,
    var photo: String?? = null,
    var video: String?? = null,
    var isActive: Boolean? = null

)