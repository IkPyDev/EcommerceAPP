package com.ikpydev.ecommerceapp.presention.product

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val product = MediatorLiveData<PagingData<Product>>()
    val category = MutableLiveData<Category>()

    fun setCategory(category: Category) {
        this.category.postValue(category)
        getProduct()
    }

    fun getProduct() = viewModelScope.launch {
        val query = ProductQuery(category = category.value)
        productRepository.getProducts(query).cachedIn(viewModelScope).collectLatest {
            product.postValue(it)
        }

    }


    fun setLoadStates(states: CombinedLoadStates) {
        val loading = states.source.append is LoadState.Loading
        this.loading.postValue(loading)
    }
    fun toggleWishlist(product: Product)= viewModelScope.launch{
        try {
            productRepository.toggleWishlist(product.id,product.wishlist)
        }catch (e:Exception){
            Log.d("TAG", "getToggleWishlist: ")
        }

    }


}