package com.ikpydev.ecommerceapp.domain.module

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserInfo(
    val fullName: String,
    val emailAddress: String,
    val phoneNumber: String,
    val address: String,
    val code: String,
    val city: String,
    val country: String,
    val promo: String? = null
): Parcelable