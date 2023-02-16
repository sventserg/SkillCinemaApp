package com.example.skillcinema.domain

import com.example.skillcinema.entity.*
import com.example.skillcinema.entity.repository.LoadMovieDataRepository

class LoadMovieDataUseCase(
    private val repository: LoadMovieDataRepository
) {
    suspend fun loadMovie(kinopoiskId: Int): Movie? {
        return repository.loadMovie(kinopoiskId)
    }

    suspend fun loadMovieForPaging(kinopoiskId: Int): Movie? {
        return repository.loadMovieForPaging(kinopoiskId)
    }

    suspend fun loadMovieStaff(kinopoiskId: Int): List<Staff>? {
        return repository.loadMovieStaff(kinopoiskId)
    }

    suspend fun loadMovieImage(kinopoiskId: Int, page: Int, type: MovieImageType): MovieImageList? {
        return repository.loadMovieImage(kinopoiskId, page, type)
    }

    suspend fun loadMovieImageForPaging(kinopoiskId: Int, page: Int, type: MovieImageType): MovieImageList? {
        return repository.loadMovieImageForPaging(kinopoiskId, page, type)
    }

    suspend fun loadSimilarMovies(kinopoiskId: Int): MovieList? {
        return repository.loadSimilarMovies(kinopoiskId)
    }
}