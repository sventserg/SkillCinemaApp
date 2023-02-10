package com.example.skillcinema.domain

import com.example.skillcinema.entity.MovieImageList
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.entity.repository.LoadMovieImageRepository

class LoadMovieImageUseCase(private val repository: LoadMovieImageRepository) {

    suspend fun loadTypedImages(kinopoiskId: Int, page: Int, type: MovieImageType): MovieImageList? {
        return repository.loadTypedImages(kinopoiskId, page, type)
    }
}