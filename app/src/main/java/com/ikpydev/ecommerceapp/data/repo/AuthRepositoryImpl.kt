package com.ikpydev.ecommerceapp.data.repo

import com.ikpydev.ecommerceapp.data.api.auth.AuthApi
import com.ikpydev.ecommerceapp.data.api.auth.dto.SingInRequest
import com.ikpydev.ecommerceapp.data.store.TokenStore
import com.ikpydev.ecommerceapp.data.store.UserStore
import com.ikpydev.ecommerceapp.domain.module.User
import com.ikpydev.ecommerceapp.domain.repo.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val userStore: UserStore
):AuthRepository {

    override suspend fun singIn(username: String, password: String):User {
        val request = SingInRequest(username, password)
        val response = authApi.singIn(request)
        tokenStore.set(response.token)
        userStore.set(response.user)
        return response.user.toUser()
    }
}