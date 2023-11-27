package com.ikpydev.ecommerceapp.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("category")
    val category: String,
    @SerializedName("discount")
    val discount: Double?,
    @SerializedName("discription")
    val discription: String,
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("in_stock")
    val inStock: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("related")
    val related: List<Product>,
    @SerializedName("reviews")
    val reviews: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("free_delivery")
    val freeDelivery: Boolean
)