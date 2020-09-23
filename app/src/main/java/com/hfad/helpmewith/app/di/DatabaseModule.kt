package com.hfad.helpmewith.app.di

import android.content.Context
import androidx.room.Room
import com.hfad.helpmewith.app.data.db.Database
import com.hfad.helpmewith.app.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesRoom(@ApplicationContext appContext: Context): Database {
        return Room
            .databaseBuilder(appContext, Database::class.java, "help-me-with-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: Database): UserDao {
        return database.userDao()
    }
}
