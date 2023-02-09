package com.example.skillcinema.di

import com.example.skillcinema.domain.*
import com.example.skillcinema.presentation.viewmodel.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule {

    @Provides
    @Singleton
    fun provideHomepageVM(
        loadApiFilters: LoadApiFilters,
        loadMovieList: LoadMovieList,
        getMonthName: GetMonthName,
        randomFilter: RandomFilter
    ): HomepageVM {
        return HomepageVM(
            loadApiFilters,
            loadMovieList,
            getMonthName,
            randomFilter
        )
    }

    @Provides
    @Singleton
    fun provideHomepageViewModel(
        loadMovieList: LoadMovieList,
        getMonthName: GetMonthName,
        randomFilter: RandomFilter,
        loadMovieData: LoadMovieData
    ): HomepageViewModel {
        return HomepageViewModel(
            loadMovieList,
            getMonthName,
            randomFilter,
            loadMovieData
        )
    }

    @Provides
    @Singleton
    fun provideMainViewModel(
        loadMovieData: LoadMovieData,
        loadApiFilters: LoadApiFilters
    ): MainViewModel {
        return MainViewModel(loadMovieData, loadApiFilters)
    }

    @Provides
    @Singleton
    fun provideMovieListPageVM(
        loadMovieList: LoadMovieList
    ): MovieListPageVM {
        return MovieListPageVM(loadMovieList)
    }

    @Provides
    @Singleton
    fun provideMoviePageVM(
        loadMovieData: LoadMovieData,
        getFilteredStaff: GetFilteredStaff,
        loadSeasons: LoadSeasons,
        seriesCalculation: SeriesCalculation
    ): MoviePageVM {
        return MoviePageVM(loadMovieData, getFilteredStaff, loadSeasons, seriesCalculation)
    }

    @Provides
    @Singleton
    fun provideStaffPersonVM(
        loadPerson: LoadPerson,
        loadMovieData: LoadMovieData,
        movieRatingSorter: MovieRatingSorter
    ): StaffPersonVM {
        return StaffPersonVM(loadPerson, loadMovieData, movieRatingSorter)
    }

    @Provides
    @Singleton
    fun provideGalleryVM(
        loadMovieImage: LoadMovieImage
    ): GalleryVM {
        return GalleryVM(loadMovieImage)
    }

    @Provides
    @Singleton
    fun provideFilmographyVM(
        getSortedMovieList: GetSortedMovieList,
        loadMovieData: LoadMovieData
    ): FilmographyVM {
        return FilmographyVM(getSortedMovieList, loadMovieData)
    }

    @Provides
    @Singleton
    fun provideProfilePageVM(
        loadMovieData: LoadMovieData
    ): ProfilePageVM {
        return ProfilePageVM(loadMovieData)
    }

    @Provides
    @Singleton
    fun provideSearchPageVM(
        search: Search,
        loadApiFilters: LoadApiFilters
    ): SearchPageVM {
        return SearchPageVM(search, loadApiFilters)
    }

    @Provides
    @Singleton
    fun provideDatabaseViewModel(
        database: Database,
        settings: GetAppPreferences
    ): DatabaseViewModel {
        return DatabaseViewModel(database, settings)
    }
}