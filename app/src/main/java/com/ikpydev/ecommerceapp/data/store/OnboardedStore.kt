package com.ikpydev.ecommerceapp.data.store

import javax.inject.Inject

class OnboardedStore @Inject constructor(): BaseStrore<Boolean>("onboarded",Boolean::class.java) {
}