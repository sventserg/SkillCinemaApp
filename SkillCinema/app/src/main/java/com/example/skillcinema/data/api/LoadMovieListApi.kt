package com.example.skillcinema.data.api

import com.example.skillcinema.data.*
import com.example.skillcinema.data.dto.MovieListDto
import com.example.skillcinema.data.dto.TopMovieListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LoadMovieListApi {
    @Headers(KINOPOISK_API_KEY)
    @GET(GET_PREMIERES_PATH)
    fun loadPremieres(
        @Query(QUERY_PARAMETER_YEAR) year: Int,
        @Query(QUERY_PARAMETER_MONTH) month: String
    ): Call<MovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_TOP_MOVIES_PATH)
    fun loadPopularMovies(
        @Query(QUERY_PARAMETER_TYPE) type: String = TYPE_POPULAR_MOVIES,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<TopMovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_TOP_MOVIES_PATH)
    fun loadBestMovies(
        @Query(QUERY_PARAMETER_TYPE) type: String = TYPE_BEST_MOVIES,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<TopMovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIES_PATH)
    fun loadTVSeries(
        @Query(QUERY_PARAMETER_TYPE) type: String = TYPE_TV_SERIES,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIES_PATH)
    fun loadMiniSeries(
        @Query(QUERY_PARAMETER_TYPE) type: String = TYPE_MINI_SERIES,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIES_PATH)
    fun loadFilteredMovies(
        @Query(QUERY_PARAMETER_COUNTRIES) countryId: Int,
        @Query(QUERY_PARAMETER_GENRES) genreId: Int,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieListDto>
}