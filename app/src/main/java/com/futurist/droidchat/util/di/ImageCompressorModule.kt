package com.futurist.droidchat.util.di

import com.futurist.droidchat.util.image.ImageCompressor
import com.futurist.droidchat.util.image.ImageCompressorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ImageCompressorModule {

    @Binds
    fun bindImageCompressor(compressor: ImageCompressorImpl): ImageCompressor
}