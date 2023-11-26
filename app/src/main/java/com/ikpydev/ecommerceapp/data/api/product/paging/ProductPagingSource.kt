package com.ikpydev.ecommerceapp.data.api.product.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ikpydev.ecommerceapp.data.api.product.ProductApi
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.domain.module.ProductQuery

class ProductPagingSource(
    private val productApi: ProductApi,
    private val query: ProductQuery
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        return try {
            val key = params.key ?: 0

            val product = productApi.getProducts(
                categoryId = query.category?.id,
                search = query.search ,
                from = query.range.first,
                to = query.range.second,
                rating = query.rating,
                discount = query.discount,
                sort = query.sort?.joinToString(),
                page = key,
                size = params.loadSize
            )

            LoadResult.Page(
                data = product,
                prevKey = params.key?.let { it- 1 }?.takeIf { it > 0 },
                nextKey = if (product.isNotEmpty()) key+1 else null

            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}