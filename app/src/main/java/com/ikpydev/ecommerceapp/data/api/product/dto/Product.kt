package com.ikpydev.ecommerceapp.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName("discount")
    val discount: Double?,
    @SerializedName("favorite")
    var favorite: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("rating_count")
    val ratingCount: Int,
    @SerializedName("title")
    val title: String
)