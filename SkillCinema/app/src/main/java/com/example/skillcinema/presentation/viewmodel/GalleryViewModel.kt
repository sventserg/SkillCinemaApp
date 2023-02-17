package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.entity.MovieImage
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.presentation.adapter.movieImage.MovieImagePagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GalleryViewModel(
    val loadMovieData: LoadMovieDataUseCase
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

    private val currentMovie = MutableStateFlow<Int?>(null)
    fun setCurrentMovie(id: Int) {
        currentMovie.value = id
    }

    private val _currentPagedImages = MutableStateFlow<Flow<PagingData<MovieImage>>?>(null)
    val currentPagedImages = _currentPagedImages.asStateFlow()

    fun setCurrentPagedImages(pagedImages: Flow<PagingData<MovieImage>>?) {
        _currentPagedImages.value = pagedImages
    }

    private val _currentListName = MutableStateFlow<String>("")
    val currentListName = _currentListName.asStateFlow()

    fun setCurrentListName(name: String) {
        _currentListName.value = name
    }

    fun pagedImages(
        type: MovieImageType
    ): Flow<PagingData<MovieImage>>? {
        val id = currentMovie.value
        return if (id != null)
            Pager(
                config = pagingConfig,
                initialKey = null,
                pagingSourceFactory = {
                    MovieImagePagingSource(
                        loadMovieData = loadMovieData,
                        kinopoiskId = id,
                        type = type
                    )
                }
            ).flow.cachedIn(viewModelScope)
        else null
    }

}