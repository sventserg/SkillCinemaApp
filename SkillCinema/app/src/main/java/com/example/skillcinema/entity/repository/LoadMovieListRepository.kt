package com.example.skillcinema.entity.repository

import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.MovieList

interface LoadMovieListRepository {

    suspend fun loadPremieres(
        year: Int,
        month: String
    ): MovieList?

    suspend fun loadPopularMovies(
        page: Int
    ): MovieList?

    suspend fun loadBestMovies(
        page: Int
    ): MovieList?

    suspend fun loadTVSeries(
        page: Int
    ): MovieList?

    suspend fun loadMiniSeries(
        page: Int
    ): MovieList?

    suspend fun loadFilteredMovies(
        country: Country?,
        genre: Genre?,
        page: Int
    ): MovieList?
}