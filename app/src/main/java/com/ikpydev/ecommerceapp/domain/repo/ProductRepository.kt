package com.ikpydev.ecommerceapp.domain.repo

import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse

interface ProductRepository {
    suspend fun getHome():HomeResponse
}