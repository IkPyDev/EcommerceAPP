package com.ikpydev.ecommerceapp.data.api.auth.dto

import com.google.gson.annotations.SerializedName
import com.ikpydev.ecommerceapp.domain.module.User

data class UserDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?
){
    fun toUser() = User(
        username = username,
        avatar = avatar,
        email =  email,
        firstName = firstName,
        lastName = lastName

    )
}
