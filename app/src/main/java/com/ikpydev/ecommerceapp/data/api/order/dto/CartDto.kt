package com.ikpydev.ecommerceapp.data.api.order.dto


import com.google.gson.annotations.SerializedName

data class CartDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("count")
    val count: Int
)