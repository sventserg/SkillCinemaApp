package com.example.skillcinema.presentation.adapter.movieList

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.data.*
import com.example.skillcinema.data.dto.MovieCountryDto
import com.example.skillcinema.data.dto.MovieDto
import com.example.skillcinema.data.dto.MovieGenreDto
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.PersonMovie
import com.squareup.moshi.Json
import kotlinx.coroutines.delay

class LoadMoviePagingSource(
    private val loadMovieData: LoadMovieDataUseCase,
    private val movieList: List<PersonMovie>
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 0
        return kotlin.runCatching {
            loadMovieList(page)
        }.fold(
            onSuccess = {
                val data = it
                if (data.isNotEmpty()) {
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )
                } else
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    private suspend fun loadMovieList(page: Int): List<Movie> {
        val newMovieList = mutableListOf<Movie>()
        if (movieList.size >= page + 1) {
            Log.d("SOURCE", "Movie id: ${movieList[page].filmId}, name: ${movieList[page].nameRu} page: $page, movie list size:${movieList.size}")
            val movie = loadMovieData.loadMovieForPaging(movieList[page].filmId)

            if (movie != null) {
                newMovieList.add(movie)
                Log.d("SOURCE_2", "Movie id: ${movie.id()}, name: ${movie.name()} page: $page, movie list size:${movieList.size}")
            }
        }
        return newMovieList
    }
}