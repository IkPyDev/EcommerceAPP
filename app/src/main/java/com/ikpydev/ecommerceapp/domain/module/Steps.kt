package com.ikpydev.ecommerceapp.domain.module

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Steps (
    @StringRes val title:Int,
    val data:String?,
    @DrawableRes val icon:Int
)