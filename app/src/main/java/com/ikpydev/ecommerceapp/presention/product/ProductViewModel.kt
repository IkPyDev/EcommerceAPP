package com.ikpydev.ecommerceapp.presention.product

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.data.api.product.ProductApi
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :ViewModel() {

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val product = MediatorLiveData<PagingData<Product>>()
    val category = MutableLiveData<Category>()

    fun setCategory(category: Category){
        this.category.postValue(category)
        getProduct()
    }

     fun getProduct() {
        val query = ProductQuery(category = category.value)
        val product = productRepository.getProduct(query)
        this.product.addSource(product){
            this.product.postValue(it)
        }
    }

    fun setLoadStates(states: CombinedLoadStates){
        val loading = states.source.append is LoadState.Loading
        this.loading.postValue(loading)
    }

}