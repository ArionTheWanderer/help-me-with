package com.hfad.helpmewith.app.di

import com.hfad.helpmewith.authentication.login.data.qualifier.SignInRepositoryMain
import com.hfad.helpmewith.authentication.login.data.repository.SignInRepository
import com.hfad.helpmewith.authentication.login.data.repository.SignInRepositoryImpl
import com.hfad.helpmewith.authentication.register.data.qualifier.SignUpRepositoryMain
import com.hfad.helpmewith.authentication.register.data.repository.SignUpRepository
import com.hfad.helpmewith.authentication.register.data.repository.SignUpRepositoryImpl
import com.hfad.helpmewith.main.profile.data.qualifier.ProfileRepositoryMain
import com.hfad.helpmewith.main.profile.data.repository.ProfileRepository
import com.hfad.helpmewith.main.profile.data.repository.ProfileRepositoryImpl
import com.hfad.helpmewith.main.search.data.qualifier.SearchRepositoryMain
import com.hfad.helpmewith.main.search.data.repository.SearchRepository
import com.hfad.helpmewith.main.search.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @SignInRepositoryMain
    @Singleton
    @Binds
    abstract fun provideSignInRepositoryMain(
        signInRepositoryImpl: SignInRepositoryImpl
    ): SignInRepository

    @SignUpRepositoryMain
    @Singleton
    @Binds
    abstract fun provideSignUpRepositoryMain(
        signUpRepositoryImpl: SignUpRepositoryImpl
    ): SignUpRepository

    @ProfileRepositoryMain
    @Singleton
    @Binds
    abstract fun provideProfileRepositoryMain(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @SearchRepositoryMain
    @Singleton
    @Binds
    abstract fun provideSearchRepositoryMain(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

}
