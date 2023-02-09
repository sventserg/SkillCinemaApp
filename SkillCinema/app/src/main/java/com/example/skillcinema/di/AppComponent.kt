package com.example.skillcinema.di

import com.example.skillcinema.presentation.viewmodel.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class])
interface AppComponent {
    fun homepageVM(): HomepageVM
    fun homepageViewModel(): HomepageViewModel
    fun movieListPageVM(): MovieListPageVM
    fun mainViewModel(): MainViewModel
    fun moviePageVM(): MoviePageVM
    fun staffPersonVM(): StaffPersonVM
    fun galleryVM(): GalleryVM
    fun filmographyVM(): FilmographyVM
    fun profilePageVM(): ProfilePageVM
    fun searchPageVM(): SearchPageVM
    fun databaseViewModel(): DatabaseViewModel
}