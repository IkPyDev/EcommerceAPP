package com.ikpydev.ecommerceapp.data.store

import com.ikpydev.ecommerceapp.data.api.auth.dto.UserDto
import javax.inject.Inject

class UserStore @Inject constructor() : BaseStore<UserDto>("user", UserDto::class.java)
