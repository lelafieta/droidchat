package com.futurist.droidchat.data.manager.selfuser

import com.futurist.droidchat.SelfUser
import kotlinx.coroutines.flow.Flow

interface SelfUserManager {
    val selfUser: Flow<SelfUser>
    suspend fun saveSelfUser(
        firstName: String,
        lastName: String,
        profilePictureUrl: String,
        username: String
    )
    suspend fun clearSelfUser()
}