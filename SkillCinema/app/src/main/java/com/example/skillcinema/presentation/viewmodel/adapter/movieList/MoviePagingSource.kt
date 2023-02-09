package com.example.skillcinema.presentation.viewmodel.adapter.movieList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.LoadMovieList
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.MovieList
import com.example.skillcinema.presentation.viewmodel.*

class MoviePagingSource(
    private val movieListType: MovieListType,
    private val loadMovieList: LoadMovieList
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            loadMovieList(movieListType, page)
        }.fold(
            onSuccess = {
                if (it != null) {
                    val data = it
                    if (data.isNotEmpty()) {
                        LoadResult.Page(
                            data = data,
                            prevKey = null,
                            nextKey = if (data.isEmpty()) null else {
                                when (movieListType) {
                                    is Premieres -> null
                                    is MovieCollectionType -> null
                                    else -> page + 1
                                }
                            }
                        )
                    } else
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
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

    private suspend fun loadMovieList(type: MovieListType, page: Int): List<Movie>? {
        var movieList: MovieList? = null
        when (type) {
            BestMovies -> {
                movieList = loadMovieList.loadBestMovies(page = page)
                return movieList?.movieList
            }
            is FilteredMovies -> {
                movieList = loadMovieList.loadFilteredMovies(type.country, type.genre, page)
                return movieList?.movieList
            }
            MiniSeries -> {
                movieList = loadMovieList.loadMiniSeries(page)
                return movieList?.movieList
            }
            PopularMovies -> {
                movieList = loadMovieList.loadPopularMovies(page)
                return movieList?.movieList
            }
            is Premieres -> {
                movieList = loadMovieList.loadPremieres(type.year, type.month)
                return movieList?.movieList
            }
            TVSeries -> {
                movieList = loadMovieList.loadTVSeries(page)
                return movieList?.movieList
            }
            is MovieCollectionType -> return type.movieList
        }
    }
}