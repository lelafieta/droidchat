package com.futurist.droidchat.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.futurist.droidchat.data.datastore.TokensKeys
import com.futurist.droidchat.data.datastore.tokenDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenManager{

    private val tokenDataStore = context.tokenDataStore

    override val accessToken: Flow<String>
        get() = tokenDataStore.data.map{preferences ->
            preferences[TokensKeys.ACCESS_TOKEN] ?: ""
        }

    override suspend fun saveAccessToken(token: String) {
        tokenDataStore.edit { preferences ->
            preferences[TokensKeys.ACCESS_TOKEN] = token
        }
    }

    override suspend fun clearAccessToken() {
        tokenDataStore.edit {preferences ->
            preferences.remove(TokensKeys.ACCESS_TOKEN)
        }
    }
}