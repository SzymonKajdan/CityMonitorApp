package com.inz.citymonitor.data.model.User

data class SignUpUser(
    var username: String,
    var password: String,
    var email:String,
    var firstname: String? = null,
    var lastname:String?=null

) {


}