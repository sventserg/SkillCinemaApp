package com.example.skillcinema.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.R
import com.example.skillcinema.presentation.viewmodel.adapter.movieList.MoviePagingSource
import com.example.skillcinema.domain.LoadMovieList
import com.example.skillcinema.entity.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class MovieListPageVM(
    private val loadMovieList: LoadMovieList
) : ViewModel() {

    private val _movieListName = MutableStateFlow<String?>(null)
    val movieListName = _movieListName.asStateFlow()

    fun setMovieListType(type: MovieListType, context: Context) {
        when (type) {
            BestMovies -> _movieListName.value = context.getString(R.string.top_250)
            is FilteredMovies -> _movieListName.value = "${
                type.genre?.name?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            } ${type.country?.name}"
            MiniSeries -> _movieListName.value = context.getString(R.string.mini_series)
            PopularMovies -> _movieListName.value = context.getString(R.string.popular)
            is Premieres -> _movieListName.value = context.getString(R.string.premieres)
            TVSeries -> _movieListName.value = context.getString(R.string.tv_series)
            is MovieCollectionType -> _movieListName.value = type.name
        }
    }

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        enablePlaceholders = true
    )

    fun getPagedList(movieListType: MovieListType): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            initialKey = null,
            pagingSourceFactory = { MoviePagingSource(movieListType, loadMovieList) }
        ).flow.cachedIn(viewModelScope)
    }


}