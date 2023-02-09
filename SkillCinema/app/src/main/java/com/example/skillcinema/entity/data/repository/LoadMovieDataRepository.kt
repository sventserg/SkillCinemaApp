package com.example.skillcinema.entity.data.repository

import com.example.skillcinema.entity.*

interface LoadMovieDataRepository {
    suspend fun loadMovie(kinopoiskId: Int): Movie?
    suspend fun loadMovieStaff(kinopoiskId: Int): List<Staff>?
    suspend fun loadMovieImage(kinopoiskId: Int, page: Int, type: MovieImageType): MovieImageList?
    suspend fun loadSimilarMovies(kinopoiskId: Int): MovieList?
}