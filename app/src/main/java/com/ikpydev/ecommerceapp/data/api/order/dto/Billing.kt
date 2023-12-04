package com.ikpydev.ecommerceapp.data.api.order.dto


import com.google.gson.annotations.SerializedName

data class Billing(
    @SerializedName("delivery")
    val delivery: Double,
    @SerializedName("discount")
    val discount: Double?,
    @SerializedName("items")
    val items: Double,
    @SerializedName("tex")
    val tex: Double,
    @SerializedName("total")
    val total: Double
)