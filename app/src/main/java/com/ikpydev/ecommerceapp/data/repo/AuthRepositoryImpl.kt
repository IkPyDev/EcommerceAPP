package com.ikpydev.ecommerceapp.data.repo

import com.ikpydev.ecommerceapp.data.api.auth.AuthApi
import com.ikpydev.ecommerceapp.data.api.auth.dto.AuthResponse
import com.ikpydev.ecommerceapp.data.api.auth.dto.SignInRequest
import com.ikpydev.ecommerceapp.data.api.auth.dto.SignUpRequest
import com.ikpydev.ecommerceapp.data.store.TokenStore
import com.ikpydev.ecommerceapp.data.store.UserStore
import com.ikpydev.ecommerceapp.domain.repo.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val userStore: UserStore
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
}