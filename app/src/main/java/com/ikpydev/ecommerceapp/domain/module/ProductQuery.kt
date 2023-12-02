package com.ikpydev.ecommerceapp.domain.module

import android.os.Parcelable
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductQuery(
    val category: Category? = null,
    val search: String? = null,
    val range :Pair<Float,Float> = 0f to 1000f,
    val rating : Int? = null,
    val discount :Int?= null,
    val sort :List<Sort>? = emptyList(),
    val wishlist :Boolean?= null
) : Parcelable

enum class Sort{
    discount,voucher,shipping,delivery
}
