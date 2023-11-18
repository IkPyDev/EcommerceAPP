package com.ikpydev.ecommerceapp.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("rating_count")
    val ratingCount: Int,
    @SerializedName("title")
    val title: String
)