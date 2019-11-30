package com.inz.citymonitor.data.model

data class ErrorResponseModel(var details:String?,var code:String?,var fields: List<Field>?)
data class Field(var fieldName:String,var details: String?)

class SuccesResponseModel(var d:String)

