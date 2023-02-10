package com.example.skillcinema.entity.repository

import com.example.skillcinema.entity.MovieImageList
import com.example.skillcinema.entity.MovieImageType

interface LoadMovieImageRepository {
    suspend fun loadTypedImages(kinopoiskId: Int, page: Int, type: MovieImageType): MovieImageList?
}