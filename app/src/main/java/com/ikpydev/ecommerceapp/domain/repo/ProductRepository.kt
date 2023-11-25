package com.ikpydev.ecommerceapp.domain.repo

import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getHome(): HomeResponse
    suspend fun getCategories(): List<Category>
    fun getProduct(query: ProductQuery): Flow<PagingData<Product>>

    fun getRecents(): Flow<List<String>>
    suspend fun clearRecents()
    suspend fun addRecent(search:String)

}