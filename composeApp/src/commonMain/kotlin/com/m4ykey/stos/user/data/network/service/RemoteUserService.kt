package com.m4ykey.stos.user.data.network.service

import com.m4ykey.stos.core.Filters.USER_FILTER
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.user.data.network.dto.UserDto

interface RemoteUserService {

    suspend fun getUserById(
        filter : String = USER_FILTER,
        id : Int
    ) : Items<UserDto>

}