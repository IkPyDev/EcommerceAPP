package com.ikpydev.ecommerceapp.presention.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.domain.repo.AuthRepository
import com.ikpydev.ecommerceapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val loading = MutableLiveData(false)
    val events = SingleLiveEvent<Events>()


    fun signUp(username: String, email: String, password: String) =
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            try {
                authRepository.signUp(username, email, password)
                events.postValue(Events.HomeNavigate)
            } catch (e: Exception) {
                when {
                    e is HttpException && e.code() == 403 -> events.postValue(Events.AlreadyRegistered)
                    e is IOException -> events.postValue(Events.ConnectionError)
                    else -> events.postValue(Events.Error)
                }

            }finally {
                loading.postValue(false)
            }


        }

    sealed class Events {
        object HomeNavigate : Events()
        object Error : Events()
        object AlreadyRegistered : Events()
        object ConnectionError : Events()


    }
}