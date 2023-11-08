package com.ikpydev.ecommerceapp.data.api.auth


import com.ikpydev.ecommerceapp.data.api.auth.dto.SingInRequest
import com.ikpydev.ecommerceapp.data.api.auth.dto.SingInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {


    @POST("/auth/sing-in/")
    suspend fun singIn(@Body request:SingInRequest):SingInResponse
}