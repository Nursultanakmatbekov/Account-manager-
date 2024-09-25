package com.nur.data.network.di

import android.accounts.AccountManager
import android.content.Context
import com.nur.data.local.AccountUtils
import com.nur.data.local.ApiAuthenticator
import com.nur.data.local.IServerAuthenticator
import com.nur.data.network.apiservice.AnimeApiService
import com.nur.data.network.apiservice.SignInApiService
import com.nur.data.network.di.interceptor.TokenInterceptor
import com.nur.data.network.repository.AnimeRepositoryImpl
import com.nur.data.network.repository.SingInRepositoryImpl
import com.nur.domain.repostories.AnimeRepository
import com.nur.domain.repostories.auth.SingInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/") // Укажите ваш базовый URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeApiService(retrofit: Retrofit): AnimeApiService {
        return retrofit.create(AnimeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignInApiService(retrofit: Retrofit): SignInApiService {
        return retrofit.create(SignInApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiAuthenticator(signInApiService: SignInApiService): IServerAuthenticator {
        return ApiAuthenticator(signInApiService)
    }

    @Provides
    @Singleton
    fun provideAccountManager(@ApplicationContext context: Context): AccountManager {
        return AccountManager.get(context)
    }

    @Provides
    @Singleton
    fun provideAccountUtils(apiAuthenticator: IServerAuthenticator): AccountUtils {
        AccountUtils.initialize(apiAuthenticator)
        return AccountUtils
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(apiService: AnimeApiService): AnimeRepository {
        return AnimeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSignInRepository(apiAuthenticator: SignInApiService): SingInRepository {
        return SingInRepositoryImpl(apiAuthenticator)
    }
}
