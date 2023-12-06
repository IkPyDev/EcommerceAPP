package com.ikpydev.ecommerceapp.domain.repo

import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.data.api.order.dto.Billing
import com.ikpydev.ecommerceapp.domain.module.Order
import com.ikpydev.ecommerceapp.domain.module.Status
import kotlinx.coroutines.flow.Flow

interface  OrderRepository {

    fun getBilling(promo:String? = null) : Flow<Billing>
    suspend fun createOrder(promo: String?)

    fun getOrders(status: Status):Flow<PagingData<Order>>
}