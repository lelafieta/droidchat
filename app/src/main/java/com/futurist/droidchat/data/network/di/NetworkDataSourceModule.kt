package com.futurist.droidchat.data.network.di


import com.futurist.droidchat.data.network.NetworkDataSource
import com.futurist.droidchat.data.network.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    @Singleton
    fun bindNetworkDataSource(networkDataSource: NetworkDataSourceImpl) : NetworkDataSource
}