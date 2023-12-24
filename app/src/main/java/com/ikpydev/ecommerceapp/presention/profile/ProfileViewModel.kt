package com.ikpydev.ecommerceapp.presention.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.data.api.auth.dto.UserDto
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val user = MutableLiveData<UserDto>()
    val loading = MutableLiveData(false)


    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        try {

            loading.postValue(true)

            productRepository.getUserInfo().collectLatest {
                user.postValue(it)
                loading.postValue(false)
            }
        } catch (e: Exception) {

        } finally {
            loading.postValue(false)

        }
    }

    fun logOut() = viewModelScope.launch(Dispatchers.Default) {
        productRepository.logout()
    }
}