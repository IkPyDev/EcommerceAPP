package com.ikpydev.ecommerceapp.data.api.auth.dto

import com.google.gson.annotations.SerializedName
import com.ikpydev.ecommerceapp.domain.module.User

data class UserDto(
    @SerializedName("username")
    val username: String
)
