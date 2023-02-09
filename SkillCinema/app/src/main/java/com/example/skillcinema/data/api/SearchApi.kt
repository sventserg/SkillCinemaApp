package com.example.skillcinema.data.api

import com.example.skillcinema.data.*
import com.example.skillcinema.data.dto.MovieListDto
import com.example.skillcinema.data.dto.PersonByNameListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApi {
    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIES_PATH)
    fun searchMovieByCountryAndGenre(
        @Query(QUERY_PARAMETER_COUNTRIES) countries: Int,
        @Query(QUERY_PARAMETER_GENRES) genres: Int,
        @Query(QUERY_PARAMETER_ORDER) order: String,
        @Query(QUERY_PARAMETER_TYPE) type: String,
        @Query(QUERY_PARAMETER_RATING_FROM) ratingFrom: Int,
        @Query(QUERY_PARAMETER_RATING_TO) ratingTo: Int,
        @Query(QUERY_PARAMETER_YEAR_FROM) yearFrom: Int,
        @Query(QUERY_PARAMETER_YEAR_TO) yearTo: Int,
        @Query(QUERY_PARAMETER_KEYWORD) keyword: String,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIES_PATH)
    fun searchMovieByCountry(
        @Query(QUERY_PARAMETER_COUNTRIES) countries: Int,
        @Query(QUERY_PARAMETER_ORDER) order: String,
        @Query(QUERY_PARAMETER_TYPE) type: String,
        @Query(QUERY_PARAMETER_RATING_FROM) ratingFrom: Int,
        @Query(QUERY_PARAMETER_RATING_TO) ratingTo: Int,
        @Query(QUERY_PARAMETER_YEAR_FROM) yearFrom: Int,
        @Query(QUERY_PARAMETER_YEAR_TO) yearTo: Int,
        @Query(QUERY_PARAMETER_KEYWORD) keyword: String,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIES_PATH)
    fun searchMovieByGenre(
        @Query(QUERY_PARAMETER_GENRES) genres: Int,
        @Query(QUERY_PARAMETER_ORDER) order: String,
        @Query(QUERY_PARAMETER_TYPE) type: String,
        @Query(QUERY_PARAMETER_RATING_FROM) ratingFrom: Int,
        @Query(QUERY_PARAMETER_RATING_TO) ratingTo: Int,
        @Query(QUERY_PARAMETER_YEAR_FROM) yearFrom: Int,
        @Query(QUERY_PARAMETER_YEAR_TO) yearTo: Int,
        @Query(QUERY_PARAMETER_KEYWORD) keyword: String,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIES_PATH)
    fun searchMovie(
        @Query(QUERY_PARAMETER_ORDER) order: String,
        @Query(QUERY_PARAMETER_TYPE) type: String,
        @Query(QUERY_PARAMETER_RATING_FROM) ratingFrom: Int,
        @Query(QUERY_PARAMETER_RATING_TO) ratingTo: Int,
        @Query(QUERY_PARAMETER_YEAR_FROM) yearFrom: Int,
        @Query(QUERY_PARAMETER_YEAR_TO) yearTo: Int,
        @Query(QUERY_PARAMETER_KEYWORD) keyword: String,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIE_PERSON_PATH)
    fun searchPersonByName(
        @Query(QUERY_PARAMETER_NAME) name: String,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<PersonByNameListDto>
}