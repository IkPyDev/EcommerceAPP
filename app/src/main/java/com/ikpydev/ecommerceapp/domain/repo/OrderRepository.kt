package com.ikpydev.ecommerceapp.domain.repo

import com.ikpydev.ecommerceapp.data.api.order.dto.Billing
import kotlinx.coroutines.flow.Flow

interface  OrderRepository {

    fun getBilling(promo:String? = null) : Flow<Billing>
    suspend fun createOrder(promo: String?)

}