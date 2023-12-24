package com.ikpydev.ecommerceapp.domain.repo

import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.data.api.auth.dto.UserDto
import com.ikpydev.ecommerceapp.data.api.order.dto.Billing
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.domain.module.Order
import com.ikpydev.ecommerceapp.domain.module.Status
import com.ikpydev.ecommerceapp.domain.module.UserInfo
import kotlinx.coroutines.flow.Flow

interface  OrderRepository {

    fun getBilling(promo:String? = null) : Flow<Billing>
    suspend fun createOrder(promo: String?,userInfo: UserInfo,card: Card)

    fun getOrders(status: Status):Flow<PagingData<Order>>

    suspend fun setUser(userInfo: UserInfo)
    suspend fun getUser():UserInfo
    suspend fun setCard(card: Card)

     fun getCard():Flow<List<Card>>

}