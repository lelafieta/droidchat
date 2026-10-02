package com.futurist.droidchat.data.manager.di

import com.futurist.droidchat.data.manager.SecureTokenManagerImpl
import com.futurist.droidchat.data.manager.TokenManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ManagerModule {

    @Binds
    @Singleton
    abstract fun bindTokenManager(
        tokenManagerImpl: SecureTokenManagerImpl
    ): TokenManager

}