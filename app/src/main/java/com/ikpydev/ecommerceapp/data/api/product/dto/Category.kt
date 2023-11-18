package com.ikpydev.ecommerceapp.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)