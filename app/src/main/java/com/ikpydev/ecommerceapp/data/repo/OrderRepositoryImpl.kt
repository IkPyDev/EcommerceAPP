package com.ikpydev.ecommerceapp.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ikpydev.ecommerceapp.data.api.order.OrderApi
import com.ikpydev.ecommerceapp.data.api.order.dto.CartDto
import com.ikpydev.ecommerceapp.data.api.order.dto.CartRequest
import com.ikpydev.ecommerceapp.data.api.order.paging.OrderPagingSource
import com.ikpydev.ecommerceapp.data.store.CartStore
import com.ikpydev.ecommerceapp.domain.module.Status
import com.ikpydev.ecommerceapp.domain.repo.OrderRepository
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderApi: OrderApi,
    private val cartStore: CartStore
) : OrderRepository {
    override fun getBilling(promo: String?) = channelFlow {
        cartStore.getFlow().distinctUntilChanged().collectLatest { carts ->
            carts ?: return@collectLatest
            val request = CartRequest(
                cart = carts.map { CartDto(id = it.id, count = it.count) },
                promoCode = promo
            )
            val billing = orderApi.getBilling(request)
            send(billing)
        }
    }

    override suspend fun createOrder(promo: String?) {
        val carts = cartStore.get() ?: return
        val request = CartRequest(
            cart = carts.map { CartDto(id = it.id, count = it.count) },
            promoCode = promo
        )
        orderApi.createOrder(request)
        cartStore.clear()
    }
    override fun getOrders(status: Status) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 20, enablePlaceholders = false),
        initialKey = 0,
        pagingSourceFactory = { OrderPagingSource(status, orderApi) }
    ).flow
}