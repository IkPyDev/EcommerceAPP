package com.ikpydev.ecommerceapp.presention.sing_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.domain.repo.AuthRepository
import com.ikpydev.ecommerceapp.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SingInViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val loading = MutableLiveData(false)
    val event = SingleLiveEvent<Event>()

    fun sinIn(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {

            authRepository.singIn(username, password)
        } catch (e: Exception) {

            when{
                e is HttpException && e.code() == 404 -> event.postValue(Event.InvalidCredentails)
                e is IOException -> event.postValue(Event.ConnectionError)
                else -> event.postValue(Event.Error)
            }

        } finally {
            loading.postValue(false)

        }


    }

    sealed class Event {
        object InvalidCredentails : Event()
        object ConnectionError : Event()
        object Error : Event()
    }


}