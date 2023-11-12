package com.ikpydev.ecommerceapp.domain.repo

interface AuthRepository {
    suspend fun signIn(username: String, password: String)
    suspend fun signUp(username: String, email: String, password: String)
}