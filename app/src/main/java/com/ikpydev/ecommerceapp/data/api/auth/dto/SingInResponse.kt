package com.ikpydev.ecommerceapp.data.api.auth.dto

import com.google.gson.annotations.SerializedName

data class SingInResponse(
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("token")
    val token: String
)
