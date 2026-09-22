package com.futurist.droidchat.data.repository

import com.futurist.droidchat.data.network.model.TokenResponse
import com.futurist.droidchat.model.CreateAccount


interface AuthRepository {
    suspend fun signUp(createAccount: CreateAccount): Result<Unit>

    suspend fun signIn(username: String, password: String)

}