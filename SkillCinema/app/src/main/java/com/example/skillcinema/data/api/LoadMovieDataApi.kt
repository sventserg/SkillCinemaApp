package com.example.skillcinema.data.api

import com.example.skillcinema.data.*
import com.example.skillcinema.data.dto.MovieDto
import com.example.skillcinema.data.dto.MovieImageListDto
import com.example.skillcinema.data.dto.MovieListDto
import com.example.skillcinema.data.dto.StaffDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface LoadMovieDataApi {
    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIE_ID_PATH)
    fun loadMovie(
        @Path("kinopoiskId") kinopoiskId: Int
    ): Call<MovieDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIE_STAFF_PATH)
    fun loadMovieStaff(
        @Query(QUERY_PARAMETER_FILM_ID) kinopoiskId: Int
    ): Call<List<StaffDto>>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIE_IMAGES_PATH)
    fun loadMovieImages(
        @Path("kinopoiskId") kinopoiskId: Int,
        @Query(QUERY_PARAMETER_TYPE) type: String,
        @Query(QUERY_PARAMETER_PAGE) page: Int
    ): Call<MovieImageListDto>

    @Headers(KINOPOISK_API_KEY)
    @GET(GET_MOVIE_SIMILARS)
    fun loadSimilarMovies(
        @Path("kinopoiskId") kinopoiskId: Int
    ): Call<MovieListDto>
}