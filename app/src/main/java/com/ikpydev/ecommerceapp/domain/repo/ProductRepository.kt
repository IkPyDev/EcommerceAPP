package com.ikpydev.ecommerceapp.domain.repo

import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.Detail
import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.domain.module.Cart
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getHome(): HomeResponse
    suspend fun getCategories(): List<Category>
    fun getProducts(query: ProductQuery): Flow<PagingData<Product>>

    fun getRecents(): Flow<List<String>>
    suspend fun clearRecents()
    suspend fun addRecent(search:String)
    suspend fun getProduct(id: String): Detail
    suspend fun toggleWishlist(productId: String, wishlist: Boolean)

    suspend fun setCart(cart:Cart)
    fun getCart() :Flow<List<Cart>>
    suspend fun clearCart()

}