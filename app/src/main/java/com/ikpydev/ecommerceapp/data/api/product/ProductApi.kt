package com.ikpydev.ecommerceapp.data.api.product

import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse
import retrofit2.http.GET

interface ProductApi {

    @GET("home")
    suspend fun getHome():HomeResponse
}