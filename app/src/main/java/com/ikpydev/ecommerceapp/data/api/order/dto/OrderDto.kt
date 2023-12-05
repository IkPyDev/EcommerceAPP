package com.ikpydev.ecommerceapp.data.api.order.dto


import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: Int,
    @SerializedName("placed")
    val placed: String,
    @SerializedName("cancelled")
    val cancelled: String?,
    @SerializedName("confirmed")
    val confirmed: String?,
    @SerializedName("delivered")
    val delivered: String?,
    @SerializedName("delivering")
    val delivering: String?,

    )