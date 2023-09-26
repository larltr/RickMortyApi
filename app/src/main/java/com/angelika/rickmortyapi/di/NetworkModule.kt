package com.angelika.rickmortyapi.di

import com.angelika.rickmortyapi.data.remote.RetrofitClient
import com.angelika.rickmortyapi.data.remote.apiservices.RickAndMortyApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    val retrofitClient: RetrofitClient = RetrofitClient()

    @Singleton
    @Provides
    fun provideRickAndMortyApiService(): RickAndMortyApiService {
        return retrofitClient.characterApi
    }
}