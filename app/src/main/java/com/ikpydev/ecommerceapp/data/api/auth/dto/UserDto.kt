package com.ikpydev.ecommerceapp.data.api.auth.dto

import com.google.gson.annotations.SerializedName
import com.ikpydev.ecommerceapp.domain.module.User

data class UserDto(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("username")
    val username: String
){
    fun toUser() = User(
        username = username,
        avatar = avatar,
        email =  email,
        firstName = firstName,
        lastName = lastName

    )
}
