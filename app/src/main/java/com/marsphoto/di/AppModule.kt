package com.marsphoto.di

import com.marsphoto.data.repository.MarsPhotoRepositoryImpl
import com.marsphoto.domain.repsitory.MarsPhotoRepository
import com.marsphoto.internet.InternetObserver
import com.marsphoto.internet.InternetObserverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class AppModule {
    @Provides
    fun provideMarsPhotoRepository(impl: MarsPhotoRepositoryImpl): MarsPhotoRepository = impl

    @Provides
    fun provideInternetObserver(impl: InternetObserverImpl): InternetObserver = impl
}