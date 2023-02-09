package com.example.skillcinema.di

import android.content.Context
import com.example.skillcinema.data.database.DatabaseRepositoryImp
import com.example.skillcinema.data.repository.*
import com.example.skillcinema.domain.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideLoadMovieList(): LoadMovieList {
        return LoadMovieList(LoadMovieListRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideLoadApiFilters(): LoadApiFilters {
        return LoadApiFilters(LoadApiFiltersRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetMonthName(): GetMonthName {
        return GetMonthName()
    }

    @Singleton
    @Provides
    fun provideRandomFilter(): RandomFilter {
        return RandomFilter()
    }

    @Singleton
    @Provides
    fun provideLoadMovieData(): LoadMovieData {
        return LoadMovieData(LoadMovieDataRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetFilteredStaff(): GetFilteredStaff {
        return GetFilteredStaff()
    }

    @Singleton
    @Provides
    fun provideLoadPerson(): LoadPerson {
        return LoadPerson(LoadPersonRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideMovieRatingSorter(): MovieRatingSorter {
        return MovieRatingSorter()
    }

    @Singleton
    @Provides
    fun provideMovieImage(): LoadMovieImage {
        return LoadMovieImage(LoadMovieImageRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetSortedMovieList(): GetSortedMovieList {
        return GetSortedMovieList()
    }

    @Singleton
    @Provides
    fun provideLoadSeasons(): LoadSeasons {
        return LoadSeasons(LoadSeasonsRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetAppPreferences(context: Context): GetAppPreferences {
        return GetAppPreferences(AppPreferencesRepositoryImp(context))
    }

    @Singleton
    @Provides
    fun provideSeriesCalculation(context: Context): SeriesCalculation {
        return SeriesCalculation(context)
    }

    @Singleton
    @Provides
    fun provideSearch(): Search {
        return Search(SearchRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideDatabase(repository: DatabaseRepositoryImp): Database {
        return Database(repository)
    }
}