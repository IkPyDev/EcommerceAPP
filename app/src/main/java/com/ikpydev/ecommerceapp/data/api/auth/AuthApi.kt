package com.ikpydev.ecommerceapp.data.api.auth


import com.ikpydev.ecommerceapp.data.api.auth.dto.SignInRequest
import com.ikpydev.ecommerceapp.data.api.auth.dto.AuthResponse
import com.ikpydev.ecommerceapp.data.api.auth.dto.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {


    @POST("auth/sign-in")
    suspend fun signIn(@Body request:SignInRequest):AuthResponse

    @POST("auth/sign-up")
    suspend fun signUp(@Body request: SignUpRequest):AuthResponse
}