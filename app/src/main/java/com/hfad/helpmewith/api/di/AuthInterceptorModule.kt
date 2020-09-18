package com.hfad.helpmewith.api.di

import com.hfad.helpmewith.api.interceptor.AuthInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor

@InstallIn(ApplicationComponent::class)
@Module
abstract class AuthInterceptorModule {
    @Binds
    abstract fun bindAuthInterceptor(basicAuthInterceptor: AuthInterceptor): Interceptor
}
