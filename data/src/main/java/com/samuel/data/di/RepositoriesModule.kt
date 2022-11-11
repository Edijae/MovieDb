package com.samuel.data.di

import com.samuel.data.repositories.MoviesRepository
import com.samuel.data.repositoryImpl.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoriesModule {

    @Binds
    abstract fun provideCitiesRepo(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository
}