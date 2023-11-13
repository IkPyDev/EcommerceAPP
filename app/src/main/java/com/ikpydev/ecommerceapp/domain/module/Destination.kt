package com.ikpydev.ecommerceapp.domain.module

sealed class Destination {

    object Home : Destination()
    object Auth : Destination()
    object Onboarding : Destination()

}