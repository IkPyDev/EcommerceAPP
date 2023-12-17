package com.ikpydev.ecommerceapp.domain.module

data class UserInfo(
    val fullName:String,
    val emailAddress:String,
    val phoneNumber: String,
    val address:String,
    val code:Int,
    val city:String,
    val country:String
)