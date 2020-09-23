package com.hfad.helpmewith.app.di

import com.hfad.helpmewith.app.data.model.LoggedUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class LoggedUserModule {
    @Provides
    @Singleton
    fun provideLoggedUser(): LoggedUser {
        return LoggedUser()
    }
}
