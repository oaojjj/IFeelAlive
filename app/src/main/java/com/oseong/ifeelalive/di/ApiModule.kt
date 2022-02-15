package com.oseong.ifeelalive.di

import com.oseong.ifeelalive.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val apiInterceptor = Interceptor {
            val originRequest = it.request()

            val addApiUrl = originRequest.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val newRequest = originRequest.newBuilder()
                .url(addApiUrl)
                .build()

            it.proceed(newRequest)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClint: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClint)
            .baseUrl(BuildConfig.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}