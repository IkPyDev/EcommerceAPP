package com.ikpydev.ecommerceapp.data.api.product

import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("home")
    suspend fun getHome(): HomeResponse

    @GET("categories")
    suspend fun getCategories(): List<Category>


    @GET("product")
    suspend fun getProducts(
        @Query("category_id") categoryId: String?,
        @Query("search") search: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<Product>
}