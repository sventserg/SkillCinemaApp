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
    fun provideHomepageViewModel(
        loadMovieList: LoadMovieListUseCase,
        getMonthName: GetMonthNameUseCase,
        randomFilter: GetRandomFilterUseCase,
        loadMovieData: LoadMovieDataUseCase
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
        loadMovieData: LoadMovieDataUseCase,
        loadApiFilters: LoadApiFiltersUseCase
    ): MainViewModel {
        return MainViewModel(loadMovieData, loadApiFilters)
    }

    @Provides
    @Singleton
    fun provideMovieListPageVM(
        loadMovieList: LoadMovieListUseCase
    ): MovieListPageViewModel {
        return MovieListPageViewModel(loadMovieList)
    }

    @Provides
    @Singleton
    fun provideMoviePageVM(
        loadMovieData: LoadMovieDataUseCase,
        getFilteredStaff: GetFilteredStaffUseCase,
        loadSeasons: LoadSeasonsUseCase,
        seriesCalculation: GetEpisodesNumberUseCase
    ): MoviePageViewModel {
        return MoviePageViewModel(loadMovieData, getFilteredStaff, loadSeasons, seriesCalculation)
    }

    @Provides
    @Singleton
    fun provideStaffPersonVM(
        loadPerson: LoadPersonUseCase,
        loadMovieData: LoadMovieDataUseCase,
        getSortedMovieList: GetSortedMovieListUseCase
    ): StaffPersonViewModel {
        return StaffPersonViewModel(loadPerson, loadMovieData, getSortedMovieList)
    }

    @Provides
    @Singleton
    fun provideGalleryVM(
        loadMovieImage: LoadMovieImageUseCase
    ): GalleryViewModel {
        return GalleryViewModel(loadMovieImage)
    }

    @Provides
    @Singleton
    fun provideFilmographyVM(
        getSortedMovieList: GetSortedMovieListUseCase,
        loadMovieData: LoadMovieDataUseCase
    ): FilmographyViewModel {
        return FilmographyViewModel(getSortedMovieList, loadMovieData)
    }

    @Provides
    @Singleton
    fun provideProfilePageVM(
        loadMovieData: LoadMovieDataUseCase
    ): ProfilePageViewModel {
        return ProfilePageViewModel(loadMovieData)
    }

    @Provides
    @Singleton
    fun provideSearchPageVM(
        search: SearchUseCase,
        loadApiFilters: LoadApiFiltersUseCase
    ): SearchPageViewModel {
        return SearchPageViewModel(search, loadApiFilters)
    }

    @Provides
    @Singleton
    fun provideDatabaseViewModel(
        database: DatabaseUseCase,
        settings: GetAppPreferencesUseCase
    ): DatabaseViewModel {
        return DatabaseViewModel(database, settings)
    }
}