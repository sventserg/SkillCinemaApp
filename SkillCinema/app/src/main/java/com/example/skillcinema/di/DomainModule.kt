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
    fun provideLoadMovieList(): LoadMovieListUseCase {
        return LoadMovieListUseCase(LoadMovieListRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideLoadApiFilters(): LoadApiFiltersUseCase {
        return LoadApiFiltersUseCase(LoadApiFiltersRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetMonthName(): GetMonthNameUseCase {
        return GetMonthNameUseCase()
    }

    @Singleton
    @Provides
    fun provideRandomFilter(): GetRandomFilterUseCase {
        return GetRandomFilterUseCase()
    }

    @Singleton
    @Provides
    fun provideLoadMovieData(): LoadMovieDataUseCase {
        return LoadMovieDataUseCase(LoadMovieDataRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetFilteredStaff(): GetFilteredStaffUseCase {
        return GetFilteredStaffUseCase()
    }

    @Singleton
    @Provides
    fun provideLoadPerson(): LoadPersonUseCase {
        return LoadPersonUseCase(LoadPersonRepositoryImp())
    }

//    @Singleton
//    @Provides
//    fun provideMovieRatingSorter(): MovieRatingSorter {
//        return MovieRatingSorter()
//    }

    @Singleton
    @Provides
    fun provideMovieImage(): LoadMovieImageUseCase {
        return LoadMovieImageUseCase(LoadMovieImageRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetSortedMovieList(): GetSortedMovieListUseCase {
        return GetSortedMovieListUseCase()
    }

    @Singleton
    @Provides
    fun provideLoadSeasons(): LoadSeasonsUseCase {
        return LoadSeasonsUseCase(LoadSeasonsRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideGetAppPreferences(context: Context): GetAppPreferencesUseCase {
        return GetAppPreferencesUseCase(AppPreferencesRepositoryImp(context))
    }

    @Singleton
    @Provides
    fun provideSeriesCalculation(context: Context): GetEpisodesNumberUseCase {
        return GetEpisodesNumberUseCase(context)
    }

    @Singleton
    @Provides
    fun provideSearch(): SearchUseCase {
        return SearchUseCase(SearchRepositoryImp())
    }

    @Singleton
    @Provides
    fun provideDatabase(repository: DatabaseRepositoryImp): DatabaseUseCase {
        return DatabaseUseCase(repository)
    }
}