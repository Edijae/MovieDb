package com.samuel.data.di

import com.samuel.data.datasource.remote.services.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    fun provideRateService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}