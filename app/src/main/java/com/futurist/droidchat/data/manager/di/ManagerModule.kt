package com.futurist.droidchat.data.manager.di

import com.futurist.droidchat.data.manager.selfuser.SelfUserManager
import com.futurist.droidchat.data.manager.selfuser.SelfUserManagerImpl
import com.futurist.droidchat.data.manager.token.SecureTokenManagerImpl
import com.futurist.droidchat.data.manager.token.TokenManager
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

    @Binds
    @Singleton
    abstract fun bindSelfUserManager(
        selfUserManagerImpl: SelfUserManagerImpl
    ): SelfUserManager


}