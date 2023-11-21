package com.ikpydev.ecommerceapp.domain.repo

import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse

interface ProductRepository {
    suspend fun getHome():HomeResponse
    suspend fun getCategories():List<Category>

}