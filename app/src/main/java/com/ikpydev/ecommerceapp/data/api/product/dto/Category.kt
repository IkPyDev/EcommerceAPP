package com.ikpydev.ecommerceapp.data.api.product.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("id")
    val id: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
):Parcelable