package com.ikpydev.ecommerceapp.presention.search

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
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {


    val loading = MutableLiveData(false)
    val product = MediatorLiveData<PagingData<Product>>()
    val query = MutableLiveData(ProductQuery())

    val recents = MutableLiveData<List<String>>()

    init {
        getRecents()
    }


    fun getProduct() = viewModelScope.launch {
        productRepository.getProducts(query.value!!).cachedIn(viewModelScope).collectLatest {
            product.postValue(it)
        }
    }


    fun setInitials(category: Category?, wishlist: Boolean) {
        query.postValue(query.value?.copy(category = category, wishlist = wishlist))
        getProduct()
    }

    fun setSearch(search: String) {
        addRecent(search)
        query.postValue(query.value?.copy(search = search))
        getProduct()

    }

    fun setLoadStates(states: CombinedLoadStates) {
        val loading = states.source.append is LoadState.Loading
        this.loading.postValue(loading)

    }

    private fun getRecents() = viewModelScope.launch {
        productRepository.getRecents().collectLatest {
            recents.postValue(it)
        }
    }

    fun clearRecent() = viewModelScope.launch {
        productRepository.clearRecents()

    }

    fun addRecent(search: String) = viewModelScope.launch {
        productRepository.addRecent(search)
    }
    fun setQuery(query: ProductQuery){
        this.query.value = query
        getProduct()
    }
}