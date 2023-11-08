package com.ikpydev.ecommerceapp.data.api.auth.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("username")
    val username: String
)
