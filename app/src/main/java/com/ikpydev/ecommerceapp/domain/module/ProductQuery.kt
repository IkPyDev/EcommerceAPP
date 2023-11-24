package com.ikpydev.ecommerceapp.domain.module

import com.ikpydev.ecommerceapp.data.api.product.dto.Category

data class ProductQuery(
    val category: Category? = null,
    val search: String? = null
)
