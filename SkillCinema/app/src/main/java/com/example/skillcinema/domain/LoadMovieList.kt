package com.example.skillcinema.domain

import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.MovieList
import com.example.skillcinema.entity.data.repository.LoadMovieListRepository

class LoadMovieList(
    private val repository: LoadMovieListRepository,
) {
    suspend fun loadPremieres(year: Int, month: String): MovieList? {
        return repository.loadPremieres(
            year = year,
            month = month
        )
    }

    suspend fun loadPopularMovies(page: Int): MovieList? {
        return repository.loadPopularMovies(page)
    }

    suspend fun loadBestMovies(page: Int): MovieList? {
        return repository.loadBestMovies(page)
    }

    suspend fun loadTVSeries(page: Int): MovieList? {
        return repository.loadTVSeries(page)
    }

    suspend fun loadMiniSeries(page: Int): MovieList? {
        return repository.loadMiniSeries(page)
    }

    suspend fun loadFilteredMovies(country: Country?, genre: Genre?, page: Int): MovieList? {
        return repository.loadFilteredMovies(
            country = country,
            genre = genre,
            page = page
        )
    }
}