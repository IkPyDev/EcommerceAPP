package com.ikpydev.ecommerceapp.data.api.order

import com.ikpydev.ecommerceapp.data.api.order.dto.Billing
import com.ikpydev.ecommerceapp.data.api.order.dto.CartRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {

    @POST("order/get-billing")
    suspend fun getBilling(@Body request: CartRequest):Billing

    @POST("order")
    suspend fun createOrder(@Body request: CartRequest)
}