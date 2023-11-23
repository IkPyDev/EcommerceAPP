package com.ikpydev.ecommerceapp.data.store

import javax.inject.Inject

class RecentsStore @Inject constructor():BaseStore<Array<String>>("recents", Array<String>::class.java) {
}