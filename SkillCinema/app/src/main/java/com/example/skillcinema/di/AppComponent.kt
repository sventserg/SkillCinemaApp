package com.example.skillcinema.di

import com.example.skillcinema.presentation.viewmodel.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class])
interface AppComponent {
    fun homepageViewModel(): HomepageViewModel
    fun movieListPageVM(): MovieListPageViewModel
    fun mainViewModel(): MainViewModel
    fun moviePageVM(): MoviePageViewModel
    fun staffPersonVM(): StaffPersonViewModel
    fun galleryVM(): GalleryViewModel
    fun filmographyVM(): FilmographyViewModel
    fun profilePageVM(): ProfilePageViewModel
    fun searchPageVM(): SearchPageViewModel
    fun databaseViewModel(): DatabaseViewModel
}