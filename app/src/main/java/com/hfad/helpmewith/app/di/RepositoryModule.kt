package com.hfad.helpmewith.app.di

import com.hfad.helpmewith.authentication.login.data.network.SignInService
import com.hfad.helpmewith.authentication.register.data.network.SignUpService
import com.hfad.helpmewith.authentication.login.data.mapper.SignInMapper
import com.hfad.helpmewith.authentication.login.data.qualifier.SignInRepositoryMain
import com.hfad.helpmewith.authentication.register.data.mapper.SignUpMapper
import com.hfad.helpmewith.authentication.login.data.repository.SignInRepository
import com.hfad.helpmewith.authentication.login.data.repository.SignInRepositoryImpl
import com.hfad.helpmewith.authentication.register.data.qualifier.SignUpRepositoryMain
import com.hfad.helpmewith.authentication.register.data.repository.SignUpRepository
import com.hfad.helpmewith.authentication.register.data.repository.SignUpRepositoryImpl
import com.hfad.helpmewith.main.profile.data.qualifier.ProfileRepositoryMain
import com.hfad.helpmewith.main.profile.data.repository.ProfileRepository
import com.hfad.helpmewith.main.profile.data.repository.ProfileRepositoryImpl
import com.hfad.helpmewith.util.SessionManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @SignInRepositoryMain
    @ActivityRetainedScoped
    @Binds
    abstract fun provideSignInRepositoryMain(
        signInRepositoryImpl: SignInRepositoryImpl
    ): SignInRepository

    @SignUpRepositoryMain
    @ActivityRetainedScoped
    @Binds
    abstract fun provideSignUpRepositoryMain(
        signUpRepositoryImpl: SignUpRepositoryImpl
    ): SignUpRepository

    @ProfileRepositoryMain
    @ActivityRetainedScoped
    @Binds
    abstract fun provideProfileRepositoryMain(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

}
