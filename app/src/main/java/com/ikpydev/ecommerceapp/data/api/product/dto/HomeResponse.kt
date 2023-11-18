package com.ikpydev.ecommerceapp.data.api.product.dto


import com.google.gson.annotations.SerializedName
import com.ikpydev.ecommerceapp.data.api.auth.dto.UserDto

data class HomeResponse(
    @SerializedName("banners")
    val banners: List<Banner>,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("cections")
    val sections: List<Section>,
    @SerializedName("user")
    val user: UserDto
)