package com.hfad.helpmewith.api.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hfad.helpmewith.BuildConfig
import com.hfad.helpmewith.authentication.login.data.network.SignInService
import com.hfad.helpmewith.authentication.register.data.network.SignUpService
import com.hfad.helpmewith.api.interceptor.AuthInterceptor
import com.hfad.helpmewith.main.profile.data.network.ProfileService
import com.hfad.helpmewith.main.search.data.network.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient().newBuilder()
        .callTimeout(40, TimeUnit.SECONDS)
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient, gson: Gson): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))

    @Singleton
    @Provides
    fun provideSignInService(retrofit: Retrofit.Builder): SignInService {
        return retrofit
            .build()
            .create(SignInService::class.java)
    }

    @Singleton
    @Provides
    fun provideSignUpService(retrofit: Retrofit.Builder): SignUpService {
        return retrofit
            .build()
            .create(SignUpService::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileService(retrofit: Retrofit.Builder): ProfileService {
        return retrofit
            .build()
            .create(ProfileService::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit.Builder): SearchService {
        return retrofit
            .build()
            .create(SearchService::class.java)
    }
}
