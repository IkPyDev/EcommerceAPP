package com.ikpydev.ecommerceapp.data.store

import com.ikpydev.ecommerceapp.domain.module.Card
import javax.inject.Inject

class CardStore @Inject constructor() :BaseStore<Array<Card>>("card", Array<Card>::class.java) {
}