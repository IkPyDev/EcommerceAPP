package com.ikpydev.ecommerceapp.data.api.order.dto


import com.google.gson.annotations.SerializedName
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.domain.module.UserInfo

data class BillingRequest(
    @SerializedName("cart")
    val cart: List<CartDto>,
    @SerializedName("promo")
    val promoCode: String?,

)