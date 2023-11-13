package com.ikpydev.ecommerceapp.data.repo

import com.ikpydev.ecommerceapp.data.api.auth.AuthApi
import com.ikpydev.ecommerceapp.data.api.auth.dto.AuthResponse
import com.ikpydev.ecommerceapp.data.api.auth.dto.SignInRequest
import com.ikpydev.ecommerceapp.data.api.auth.dto.SignUpRequest
import com.ikpydev.ecommerceapp.data.store.OnboardedStore
import com.ikpydev.ecommerceapp.data.store.TokenStore
import com.ikpydev.ecommerceapp.data.store.UserStore
import com.ikpydev.ecommerceapp.domain.module.Destination
import com.ikpydev.ecommerceapp.domain.repo.AuthRepository
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val userStore: UserStore,
    private val onboardedStore: OnboardedStore
) : AuthRepository {

    override suspend fun signIn(username: String, password: String) {
        val request = SignInRequest(username, password)
        val response = authApi.signIn(request)
        extracted(response)


    }

    private suspend fun extracted(response: AuthResponse) {
        tokenStore.set(response.token)
        userStore.set(response.user)
    }


    override suspend fun signUp(username: String, email: String, password: String) {
        val request = SignUpRequest(username, email, password)
        val response = authApi.signUp(request)
        extracted(response)

    }

    override fun destinationFlow() = channelFlow {
        suspend fun sendDestination() {
            when {
                tokenStore.get() != null -> send(Destination.Home)
                onboardedStore.get() == true -> send(Destination.Auth)
                else -> send(Destination.Onboarding)

            }
        }
        launch {
            tokenStore.getFlow().collectLatest {
                sendDestination()
            }
        }

        launch {
            onboardedStore.getFlow().collectLatest {
                sendDestination()
            }
        }
    }

    override suspend fun onboarded() = onboardedStore.set(true)
}