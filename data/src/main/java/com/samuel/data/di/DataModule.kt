package com.samuel.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [RepositoriesModule::class, RetrofitModule::class,
        ServiceModule::class, DbModule::class]
)
@InstallIn(SingletonComponent::class)
class DataModule