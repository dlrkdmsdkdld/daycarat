package com.makeus.daycarat.hilt

import com.makeus.daycarat.DayCaratApplication
import com.makeus.daycarat.repository.GalleryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

//    @Singleton
//    @Provides
//    fun provideAuthRepository(apimodule: RetrofitInterface): AuthRepositoryIm {
//        return AuthRepository(apimodule) //레트로핏 변수를 의존성 주입을 이용해서 액티비티에서 만들지 않더라도 액티비티에서 항상 사용 가능하도록만듬
//    }

    @Singleton
    @Provides
    fun provideGalleryRepository(): GalleryRepository {
        return GalleryRepository(DayCaratApplication.mAppContext!!) //레트로핏 변수를 의존성 주입을 이용해서 액티비티에서 만들지 않더라도 액티비티에서 항상 사용 가능하도록만듬
    }


}