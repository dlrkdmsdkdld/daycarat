package com.makeus.daycarat.presentation.di

import com.makeus.daycarat.data.source.EpisodeRemoteSource
import com.makeus.daycarat.data.source.UserRemoteSource
import com.makeus.daycarat.domain.source.EpisodeSource
import com.makeus.daycarat.domain.source.UserSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindEpisodeRemoteSource(episodeRemoteSource: EpisodeRemoteSource) : EpisodeSource


    @Binds
    abstract fun bindUserRemoteSource(userRemoteSource: UserRemoteSource) : UserSource
}

