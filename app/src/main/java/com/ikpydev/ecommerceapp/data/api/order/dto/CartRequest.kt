package com.ikpydev.ecommerceapp.data.api.order.dto


import com.google.gson.annotations.SerializedName
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.domain.module.UserInfo

data class CartRequest(
    @SerializedName("cart")
    val cart: List<CartDto>,
    @SerializedName("promo")
    val promoCode: String?,
    @SerializedName("user")
    val userInfo: UserInfo,
    @SerializedName("card")
    val card: Card
)