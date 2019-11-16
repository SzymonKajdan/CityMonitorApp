package com.inz.citymonitor.data.model.User

data class LocalUser ( var id:Long?=null,
                       var username:String?=null,
                       var  firstname:String?=null,
                       var surname:String?=null,
                       var email:String?=null,
                       var isBanned:Boolean?=null,
                       var userRating:Double?=null)