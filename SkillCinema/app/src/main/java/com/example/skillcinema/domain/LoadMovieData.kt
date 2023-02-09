package com.example.skillcinema.domain

import com.example.skillcinema.entity.*
import com.example.skillcinema.entity.data.repository.LoadMovieDataRepository

class LoadMovieData(
    private val repository: LoadMovieDataRepository
) {
    suspend fun loadMovie(kinopoiskId: Int): Movie? {
        return repository.loadMovie(kinopoiskId)
    }

    suspend fun loadMovieStaff(kinopoiskId: Int): List<Staff>? {
        return repository.loadMovieStaff(kinopoiskId)
    }

    suspend fun loadMovieImage(kinopoiskId: Int, page: Int, type: MovieImageType): MovieImageList? {
        return repository.loadMovieImage(kinopoiskId, page, type)
    }

    suspend fun loadSimilarMovies(kinopoiskId: Int): MovieList? {
        return repository.loadSimilarMovies(kinopoiskId)
    }
}