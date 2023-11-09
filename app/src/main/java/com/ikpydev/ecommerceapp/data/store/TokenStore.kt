package com.ikpydev.ecommerceapp.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class TokenStore(private val dataStore: DataStore<Preferences>) {

    private val key = stringPreferencesKey("token")

    suspend fun set(value: String) {
        dataStore.edit {
            it[key] = value
        }

    }

    suspend fun get() = dataStore.data.map { it[key] }.firstOrNull()

    suspend fun clear(){
        dataStore.edit {
            it.remove(key)
        }
    }
}