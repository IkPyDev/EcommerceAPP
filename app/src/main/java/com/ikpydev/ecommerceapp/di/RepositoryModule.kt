package com.ikpydev.ecommerceapp.di

import com.ikpydev.ecommerceapp.data.repo.AuthRepositoryImpl
import com.ikpydev.ecommerceapp.data.repo.ProductRepositoryImpl
import com.ikpydev.ecommerceapp.domain.repo.AuthRepository
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepositoryImpl(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindProductRepositoryImpl(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}