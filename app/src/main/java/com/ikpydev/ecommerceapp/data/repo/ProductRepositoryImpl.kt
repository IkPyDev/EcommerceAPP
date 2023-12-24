package com.ikpydev.ecommerceapp.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ikpydev.ecommerceapp.data.api.auth.dto.UserDto
import com.ikpydev.ecommerceapp.data.api.product.ProductApi
import com.ikpydev.ecommerceapp.data.api.product.dto.HomeResponse
import com.ikpydev.ecommerceapp.data.api.product.paging.ProductPagingSource
import com.ikpydev.ecommerceapp.data.store.CardStore
import com.ikpydev.ecommerceapp.data.store.CartStore
import com.ikpydev.ecommerceapp.data.store.RecentsStore
import com.ikpydev.ecommerceapp.data.store.TokenStore
import com.ikpydev.ecommerceapp.data.store.UserInfoStore
import com.ikpydev.ecommerceapp.data.store.UserStore
import com.ikpydev.ecommerceapp.domain.module.Cart
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val userStore: UserStore,
    private val recentsStore: RecentsStore,
    private val cartStore : CartStore,
    private val tokenStore: TokenStore,
    private val userInfoStore: UserInfoStore,
    private val cardStore: CardStore
) : ProductRepository {
    override suspend fun getHome(): HomeResponse {
        val response = productApi.getHome()
        userStore.set(response.user)
        return response
    }

    override suspend fun getCategories() = productApi.getCategories()
    override fun getProducts(query: ProductQuery) = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        initialKey = 0,
        pagingSourceFactory = {
            ProductPagingSource(productApi, query)
        }


    ).flow

    override fun getRecents() = recentsStore.getFlow().map { it?.toList() ?: emptyList() }
    override suspend fun clearRecents() = recentsStore.clear()
    override suspend fun addRecent(search: String) {
        val recents = (recentsStore.get() ?: emptyArray()).toMutableList()
        recents.remove(search)
        recents.add(0, search)
        recentsStore.set(recents.toTypedArray())
    }

    override suspend fun toggleWishlist(productId: String, wishlist: Boolean) {
        productApi.toggleWishlist(productId,wishlist)
    }

    override suspend fun setCart(cart: Cart) {
        val carts = (cartStore.get() ?: emptyArray())
            .toList()
            .filterNot { it.id == cart.id }
            .toMutableList()
        if (cart.count > 0){
            carts.add(cart)
        }
        cartStore.set(carts.toTypedArray())
    }

    override fun getCart(): Flow<List<Cart>>  =cartStore.getFlow().map { it?.toList() ?: emptyList() }

    override suspend fun clearCart() = cartStore.clear()
    override suspend fun getUserInfo(): Flow<UserDto> = userStore.getFlow().map { it!! }
    override suspend fun logout() {
        cartStore.clear()
        userStore.clear()
        tokenStore.clear()
        cardStore.clear()
        recentsStore.clear()
        userInfoStore.clear()


    }

    override suspend fun getProduct(id: String) = productApi.getProduct(id)
}