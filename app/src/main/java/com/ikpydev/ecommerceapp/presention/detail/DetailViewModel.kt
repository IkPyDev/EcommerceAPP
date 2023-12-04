package com.ikpydev.ecommerceapp.presention.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.data.api.product.dto.Detail
import com.ikpydev.ecommerceapp.domain.module.Cart
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {


    val loading = MutableLiveData(false)
    val wishlist = MutableLiveData(false)
    val error = MutableLiveData(false)
    val detail = MutableLiveData<Detail>()
    val count =MutableLiveData(1)

    fun getProduct(id: String) = viewModelScope.launch {
        loading.postValue(true)
        error.postValue(false)
        try {
            val result = productRepository.getProduct(id)
            detail.postValue(result)
            wishlist.postValue(result.wishlist)
        } catch (e: Exception) {
            error.postValue(true)
        } finally {
            loading.postValue(false)
        }
    }

    fun increment(){
        var current = count.value ?: 1
        val product = detail.value ?: return
        if (current == product.inStock) return
        current++
        count.postValue(current)
    }

    fun decrement(){
        var current = count.value ?: 1
        if (current == 1) return
        current--
        count.postValue(current)
    }

    fun toggleWishlist() = viewModelScope.launch{
        val wishlistValue = wishlist.value ?: return@launch
       wishlist.postValue(wishlistValue.not())
        try {
            productRepository.toggleWishlist(detail.value!!.id,wishlistValue.not())
        }catch (e:Exception){

        }
    }
    fun set()=viewModelScope.launch {
        val detail = this@DetailViewModel.detail.value ?: return@launch
        val cart = Cart(
            id = detail.id,
            title = detail.title,
            image = detail.images.first(),
            category = detail.category,
            price = detail.price,
            discount = detail.discount,
            count = count.value ?: return@launch,
            stock = detail.inStock
        )
        productRepository.setCart(cart)
    }
}