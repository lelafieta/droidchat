package com.futurist.droidchat.data.manager.selfuser

import android.content.Context
import com.futurist.droidchat.data.datastore.selfUserStore
import com.futurist.droidchat.data.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SelfUserManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SelfUserManager{

    private val selfUserStore = context.selfUserStore

    override val selfUser: kotlinx.coroutines.flow.Flow<com.futurist.droidchat.SelfUser>
        get() = selfUserStore.data

    override suspend fun saveSelfUser(
        firstName: String,
        lastName: String,
        profilePictureUrl: String,
        username: String
    ) {
        withContext(ioDispatcher){
            selfUserStore.updateData { currentSelfUser ->
                currentSelfUser.toBuilder()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setProfilePictureUrl(profilePictureUrl)
                    .setUsername(username)
                    .build()
            }
        }
    }

    override suspend fun clearSelfUser() {
        withContext(ioDispatcher){
            selfUserStore.updateData { currentSelfUser ->
                currentSelfUser.toBuilder()
                    .clearFirstName()
                    .clearLastName()
                    .clearProfilePictureUrl()
                    .clearUsername()
                    .build()
            }
        }
    }
}