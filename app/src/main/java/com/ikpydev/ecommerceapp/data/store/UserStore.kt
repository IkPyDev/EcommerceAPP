package com.ikpydev.ecommerceapp.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.ikpydev.ecommerceapp.data.api.auth.dto.UserDto
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStore @Inject constructor() : BaseStrore<UserDto>("token", UserDto::class.java)
