package com.ikpydev.ecommerceapp.data.api.auth.dto

import com.google.gson.annotations.SerializedName


data class SingInRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String

)
