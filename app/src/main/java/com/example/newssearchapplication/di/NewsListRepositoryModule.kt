package com.example.newssearchapplication.di

import com.example.newssearchapplication.data.remote.NewsApi
import com.example.newssearchapplication.domain.repository.NewsListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsListRepositoryModule {

    @Provides
    @Singleton
    fun provideNewsListRepository(api: NewsApi): NewsListRepository{
        return NewsListRepository(api)
    }

}