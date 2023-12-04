package com.ikpydev.ecommerceapp.data.store

import com.ikpydev.ecommerceapp.domain.module.Cart
import javax.inject.Inject

class CartStore @Inject constructor() :BaseStore<Array<Cart>>("cart", Array<Cart>::class.java) {
}