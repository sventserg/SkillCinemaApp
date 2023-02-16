package com.example.skillcinema.presentation.adapter.movieList

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.SearchUseCase
import com.example.skillcinema.entity.Movie

class SearchMoviePagingSource(
    private val search: SearchUseCase,
    private val countries: Int?, private val genres: Int?,
    private val order: String, private val type: String,
    private val ratingFrom: Int, private val ratingTo: Int,
    private val yearFrom: Int, private val yearTo: Int,
    private val keyword: String,
    private val viewedMovies: List<Movie>,
    private val isViewed: Boolean
) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            loadSearchResult(page)
        }.fold(
            onSuccess = {
                if (it != null) {
                    LoadResult.Page(
                        data = checkIsViewed(it),
                        prevKey = null,
                        nextKey = if (it.isEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private suspend fun loadSearchResult(page: Int): List<Movie>? {
        Log.d(
            "PAGING_SOURCE",
            "Filters: Countries:$countries, Genres:$genres, " +
                    "Order:$order, Type:$type, RatingFrom:$ratingFrom, RatingTo:$ratingTo, " +
                    "YearFrom:$yearFrom, YearTo:$yearTo, Keyword:$keyword, " +
                    "Viewed Movies:${viewedMovies.size}, Is viewed:$isViewed"
        )
        return if (countries != null && genres != null) {
            search.searchMovieByCountryAndGenre(
                countries, genres,
                order, type,
                ratingFrom, ratingTo,
                yearFrom, yearTo,
                keyword, page
            )?.movieList
        } else if (countries != null) {
            search.searchMovieByCountry(
                countries, order, type,
                ratingFrom, ratingTo, yearFrom,
                yearTo, keyword, page
            )?.movieList
        } else if (genres != null) {
            search.searchMovieByGenre(
                genres, order, type,
                ratingFrom, ratingTo, yearFrom,
                yearTo, keyword, page
            )?.movieList
        } else {
            search.searchMovie(
                order, type,
                ratingFrom, ratingTo,
                yearFrom, yearTo,
                keyword, page
            )?.movieList
        }
    }

    private fun checkIsViewed(movieList: List<Movie>): List<Movie> {
        val newMovieList = mutableListOf<Movie>()
        if (isViewed) {
            var match = false
            movieList.forEach { movie ->
                viewedMovies.forEach {
                    if (it.id() == movie.id()) {
                        match = true
                    }
                }
                if (match) newMovieList.add(movie)
                match = false
            }
        } else {
            var match = true
            movieList.forEach { movie ->
                viewedMovies.forEach { if (it.id() == movie.id()) match = false }
                if (match) newMovieList.add(movie)
                match = true
            }
        }
        return newMovieList
    }
}