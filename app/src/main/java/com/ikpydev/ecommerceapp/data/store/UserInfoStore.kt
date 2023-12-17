package com.ikpydev.ecommerceapp.data.store

import com.ikpydev.ecommerceapp.domain.module.UserInfo
import javax.inject.Inject

class UserInfoStore @Inject constructor(): BaseStore<UserInfo>("userInfo",UserInfo::class.java) {
}