package com.makeus.daycarat.presentation.di

import com.makeus.daycarat.data.repository.AuthRepositoryImpl
import com.makeus.daycarat.data.repository.EpisodeRepositoryImpl
import com.makeus.daycarat.data.repository.FcmRepositoryImpl
import com.makeus.daycarat.data.repository.UserInfoRepositoryImpl
import com.makeus.daycarat.domain.repository.AuthRepository
import com.makeus.daycarat.domain.repository.EpisodeRepository
import com.makeus.daycarat.domain.repository.FcmRepository
import com.makeus.daycarat.domain.repository.UserInfoRepository
import com.makeus.daycarat.hilt.ApiModule
import com.makeus.daycarat.hilt.FcmApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    abstract fun bindEpisodeRepository(episodeRepositoryImpl: EpisodeRepositoryImpl) : EpisodeRepository

    @Binds
    abstract fun bindUserInfoRepositoryImpl(userInfoRepositoryImpl: UserInfoRepositoryImpl) : UserInfoRepository


    @Binds
    abstract fun bindFcmRepositoryImpl(fcmRepositoryImpl: FcmRepositoryImpl) : FcmRepository







}
//@Provides
//@Singleton
//fun provideFCMService(@ApiModule.Auth retrofit: Retrofit): FcmApi = retrofit.create(FcmApi::class.java)
