package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.domain.LoadMovieImageUseCase
import com.example.skillcinema.entity.MovieImage
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.presentation.adapter.movieImage.MovieImagePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GalleryViewModel(
    val loadMovieImage: LoadMovieImageUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        enablePlaceholders = true
    )

    private val _image = MutableStateFlow<MovieImage?>(null)
    val image = _image.asStateFlow()

    fun setImage(image: MovieImage?) {
        _image.value = image
    }

    private val _currentPagedImages = MutableStateFlow<Flow<PagingData<MovieImage>>?>(null)
    val currentPagedImages = _currentPagedImages.asStateFlow()

    fun setCurrentPagedImages(pagedImages: Flow<PagingData<MovieImage>>) {
        _currentPagedImages.value = pagedImages
    }

    fun pagedImages(
        kinopoiskId: Int,
        type: MovieImageType
    ): Flow<PagingData<MovieImage>> {
        return Pager(
            config = pagingConfig,
            initialKey = null,
            pagingSourceFactory = {
                MovieImagePagingSource(
                    loadMovieImage = loadMovieImage,
                    kinopoiskId = kinopoiskId,
                    type = type
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

}