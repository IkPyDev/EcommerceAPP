package com.ikpydev.ecommerceapp.presention.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import com.ikpydev.ecommerceapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

        val categories = MutableLiveData<List<Category>>()
        val events = SingleLiveEvent<Event>()

    init {
        getCategories()
    }


    fun getCategories() = viewModelScope.launch {
        try {
            val result  = productRepository.getCategories()
            categories.value = result
        }catch (e:Exception) { events.postValue(Event.CategoryError)}
    }
}





sealed class Event{
    object  CategoryError : Event()
}