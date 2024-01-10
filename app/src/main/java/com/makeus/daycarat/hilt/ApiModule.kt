package com.makeus.daycarat.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideAPIService(): RetrofitInterface{
        return RetrofitInterface.create()
    }

}