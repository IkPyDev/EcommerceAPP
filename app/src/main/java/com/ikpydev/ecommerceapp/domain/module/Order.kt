package com.ikpydev.ecommerceapp.domain.module

import com.ikpydev.ecommerceapp.R
import java.util.Date

data class Order(
    val id: Int,
    val items: Int,
    val placed: Date?,
    val cancelled: Date?,
    val confirmed: Date?,
    val delivering: Date?,
    val delivered: Date?,
) {
    val background: Int
        get() = when {
            delivered != null -> R.color.green
            cancelled != null -> R.color.red
            else -> R.color.on_delivery


        }

    val foreground :Int get() = when {
        delivered != null || cancelled != null -> R.color.white
        else -> R.color.yellow
    }

    val steps :List<Steps> get() {
        val list = mutableListOf<Steps>()

    }
}
