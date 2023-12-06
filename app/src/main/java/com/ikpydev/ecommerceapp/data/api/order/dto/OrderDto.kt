package com.ikpydev.ecommerceapp.data.api.order.dto


import com.google.gson.annotations.SerializedName
import com.ikpydev.ecommerceapp.domain.module.Order
import java.text.SimpleDateFormat

data class OrderDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: Int,
    @SerializedName("placed")
    val placed: String,
    @SerializedName("cancelled")
    val cancelled: String?,
    @SerializedName("confirmed")
    val confirmed: String?,
    @SerializedName("delivered")
    val delivered: String?,
    @SerializedName("delivering")
    val delivering: String?,

    ){
    fun toOrder(
        serverFormat :SimpleDateFormat,
        orderFormat:SimpleDateFormat
    ) =Order(
        id = id,
        items = items,
        placed = placed,
        confirmed = confirmed?.let { orderFormat.format(serverFormat.parse(it)!!) },
        cancelled = cancelled?.let { orderFormat.format(serverFormat.parse(it)!!) },
        delivered = delivered?.let { orderFormat.format(serverFormat.parse(it)!!) },
        delivering = delivering?.let { orderFormat.format(serverFormat.parse(it)!!) },
    )
}