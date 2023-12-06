package com.ikpydev.ecommerceapp.data.api.order

import com.ikpydev.ecommerceapp.data.api.order.dto.Billing
import com.ikpydev.ecommerceapp.data.api.order.dto.CartRequest
import com.ikpydev.ecommerceapp.data.api.order.dto.OrderDto
import com.ikpydev.ecommerceapp.domain.module.Status
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApi {

    @POST("orders/get-billing")
    suspend fun getBilling(@Body request: CartRequest):Billing

    @POST("orders")
    suspend fun createOrder(@Body request: CartRequest)

    @GET("orders")
    suspend fun getOrders(
        @Query("status") status: Status,
        @Query("size") size:Int,
        @Query("page") page:Int
    ):List<OrderDto>
}