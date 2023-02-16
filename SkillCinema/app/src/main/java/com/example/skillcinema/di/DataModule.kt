package com.example.skillcinema.di

import android.app.Application
import android.content.Context
import com.example.skillcinema.data.database.DatabaseRepositoryImp
import com.example.skillcinema.data.database.DatabaseStorage
import com.example.skillcinema.data.repository.*
import com.example.skillcinema.data.preferences.AppPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(
    private val application: Application
) {

    @Singleton
    @Provides
    fun provideLoadMovieListRepositoryImp(): LoadMovieListRepositoryImp {
        return LoadMovieListRepositoryImp()
    }

    @Singleton
    @Provides
    fun provideLoadApiFilterRepositoryImp(): LoadApiFiltersRepositoryImp {
        return LoadApiFiltersRepositoryImp()
    }

    @Singleton
    @Provides
    fun provideLoadMovieDataRepositoryImp(): LoadMovieDataRepositoryImp {
        return LoadMovieDataRepositoryImp()
    }

    @Singleton
    @Provides
    fun provideLoadPersonRepositoryImp(): LoadPersonRepositoryImp {
        return LoadPersonRepositoryImp()
    }

    @Singleton
    @Provides
    fun provideLoadSeasonsRepositoryImp(): LoadSeasonsRepositoryImp {
        return LoadSeasonsRepositoryImp()
    }

    @Singleton
    @Provides
    fun provideAppSettingsRepositoryImp(context: Context): AppPreferencesRepositoryImp {
        return AppPreferencesRepositoryImp(context)
    }

    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun provideSearchRepositoryImp(): SearchRepositoryImp {
        return SearchRepositoryImp()
    }

    @Singleton
    @Provides
    fun provideDatabaseStorage(context: Context): DatabaseStorage {
        return DatabaseStorage(context)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepositoryImp(databaseStorage: DatabaseStorage): DatabaseRepositoryImp {
        return DatabaseRepositoryImp(databaseStorage)
    }

    @Singleton
    @Provides
    fun provideAppSettingsReferences(context: Context): AppPreferences {
        return AppPreferences(context)
    }

}