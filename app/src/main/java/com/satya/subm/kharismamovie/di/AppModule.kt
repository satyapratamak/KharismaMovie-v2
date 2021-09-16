package com.satya.subm.kharismamovie.di

import android.app.Application
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovieAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
//@Singleton
object AppModule {

    @Provides
    @Singleton
    fun providerRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(NowPlayingMovieAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNowPlayingMovieAPI(retrofit: Retrofit) : NowPlayingMovieAPI =
        retrofit.create(NowPlayingMovieAPI::class.java)
}