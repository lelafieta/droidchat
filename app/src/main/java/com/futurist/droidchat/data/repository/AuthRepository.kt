package com.futurist.droidchat.data.repository

import com.futurist.droidchat.data.network.model.TokenResponse
import com.futurist.droidchat.model.CreateAccount
import com.futurist.droidchat.model.Image


interface AuthRepository {
    suspend fun getAccessToken(): String?
    suspend fun clearAccessToken()
    suspend fun signUp(createAccount: CreateAccount): Result<Unit>
    suspend fun signIn(username: String, password: String): Result<Unit>
    suspend fun uploadProfilePicture(fileString: String) : Result<Image>
    suspend fun authenticate(token: String): Result<Unit>
}