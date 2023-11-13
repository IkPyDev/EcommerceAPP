package com.ikpydev.ecommerceapp.domain.repo

import com.ikpydev.ecommerceapp.domain.module.Destination
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(username: String, password: String)
    suspend fun signUp(username: String, email: String, password: String)

    fun destinationFlow(): Flow<Destination>
    suspend fun onboarded()

}