package com.ikpydev.ecommerceapp.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("category")
    val category: Category,
    @SerializedName("image")
    val image: String,
    @SerializedName("products")
    val products: Product
)