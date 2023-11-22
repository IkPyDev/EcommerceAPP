package com.ikpydev.ecommerceapp.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ikpydev.ecommerceapp.data.api.product.ProductApi
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.data.api.product.paging.ProductPagingSource
import com.ikpydev.ecommerceapp.data.store.UserStore
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val userStore: UserStore
):ProductRepository  {
    override suspend fun getHome(): HomeResponse {
        val response = productApi.getHome()
        userStore.set(response.user)
        return response
    }

    override suspend fun getCategories()= productApi.getCategories()
    override fun getProduct(query: ProductQuery)= Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 10, enablePlaceholders = false, initialLoadSize = 20),
        initialKey = 0,
        pagingSourceFactory = {
            ProductPagingSource(productApi,query)
        }


    ).liveData

}