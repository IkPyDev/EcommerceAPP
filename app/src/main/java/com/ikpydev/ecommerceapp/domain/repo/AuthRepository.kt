package com.ikpydev.ecommerceapp.domain.repo

import com.ikpydev.ecommerceapp.domain.module.User

interface AuthRepository {
    suspend fun singIn(username: String, password: String):User
}