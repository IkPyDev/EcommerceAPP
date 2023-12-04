package com.ikpydev.ecommerceapp.domain.module

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("id")
    val id:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("image")
    val image:String,
    @SerializedName("category")
    val category:String,
    @SerializedName("price")
    val price:Double,
    @SerializedName("discount")
    val discount:Double?,
    @SerializedName("count")
    var count:Int,
    @SerializedName("stock")
    val stock:Int
)
