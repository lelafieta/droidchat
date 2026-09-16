package com.futurist.droidchat.data.repository.di

import com.futurist.droidchat.data.repository.AuthRepository
import com.futurist.droidchat.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {


    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl) : AuthRepository

}