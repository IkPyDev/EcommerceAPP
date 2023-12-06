package com.ikpydev.ecommerceapp.data.api.order.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ikpydev.ecommerceapp.data.api.order.OrderApi
import com.ikpydev.ecommerceapp.domain.module.Order
import com.ikpydev.ecommerceapp.domain.module.Status
import java.text.SimpleDateFormat
import java.util.Locale

class OrderPagingSource(
    private val status: Status,
    private val api: OrderApi
) : PagingSource<Int, Order>() {


    private val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private val orderFormat get() = SimpleDateFormat("h:MM a, d, MMMM yyyy", Locale.getDefault())
    override fun getRefreshKey(state: PagingState<Int, Order>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> {

        val page = params.key ?: 0

        return try {
            val orders = api.getOrders(status, page, params.loadSize)
            LoadResult.Page(
                data = orders.map { it.toOrder(serverFormat, orderFormat) },
                prevKey = (page - 1).takeIf { it > 0 },
                nextKey = (page + 1).takeIf { orders.isEmpty() }
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }


}