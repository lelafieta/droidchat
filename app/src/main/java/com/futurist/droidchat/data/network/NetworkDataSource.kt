package com.futurist.droidchat.data.network

import com.futurist.droidchat.data.network.model.AuthRequest
import com.futurist.droidchat.data.network.model.CreateAccountRequest
import com.futurist.droidchat.data.network.model.TokenResponse

interface NetworkDataSource {
    suspend fun signUp(request: CreateAccountRequest)
    suspend fun signIn(request: AuthRequest): TokenResponse
}