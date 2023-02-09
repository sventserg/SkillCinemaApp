package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.dto.MovieDto
import com.example.skillcinema.data.dto.MovieImageListDto
import com.example.skillcinema.data.dto.MovieListDto
import com.example.skillcinema.data.dto.StaffDto
import com.example.skillcinema.data.retrofit.LoadMovieDataRetrofit
import com.example.skillcinema.entity.*
import com.example.skillcinema.entity.data.repository.LoadMovieDataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadMovieDataRepositoryImp : LoadMovieDataRepository {

    private val movie = MutableStateFlow<Movie?>(null)
    private val movieStaff = MutableStateFlow<List<Staff>>(emptyList())
    private val similarMovies = MutableStateFlow<MovieList?>(null)
    private val movieImageList = MutableStateFlow<MovieImageList?>(null)

    private var movieIsLoaded = false
    private var movieStaffIsLoaded = false
    private var similarMoviesIsLoaded = false
    private var movieImageListIsLoaded = false

    private fun movieResponse(kinopoiskId: Int) {
        LoadMovieDataRetrofit.loadMovieDataApi.loadMovie(kinopoiskId)
            .enqueue(object : Callback<MovieDto> {
                override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                    val responseCode = response.code()
                    movie.value = response.body()
                    Log.d(RETROFIT_TAG, "Response code, load movie: $responseCode")
                    movieIsLoaded = true
                }

                override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                    Log.e(RETROFIT_TAG, "${t.message}")
                    movie.value = null
                    movieIsLoaded = true
                }
            })
    }

    override suspend fun loadMovie(kinopoiskId: Int): Movie? {
        movieResponse(kinopoiskId)
        while (!movieIsLoaded) {
            delay(100)
        }
        movieIsLoaded = false
        return movie.value
    }

    private fun movieStaffResponse(kinopoiskId: Int) {
        LoadMovieDataRetrofit.loadMovieDataApi.loadMovieStaff(kinopoiskId)
            .enqueue(object : Callback<List<StaffDto>> {
                override fun onResponse(
                    call: Call<List<StaffDto>>,
                    response: Response<List<StaffDto>>
                ) {
                    val responseCode = response.code()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        movieStaff.value = responseBody
                    } else movieStaff.value = emptyList()

                    Log.d(RETROFIT_TAG, "Response code, load movie staff: $responseCode")
                    movieStaffIsLoaded = true
                }

                override fun onFailure(call: Call<List<StaffDto>>, t: Throwable) {
                    Log.e(RETROFIT_TAG, "${t.message}")
                    movieStaff.value = emptyList()
                    movieStaffIsLoaded = true
                }
            })
    }

    override suspend fun loadMovieStaff(kinopoiskId: Int): List<Staff> {
        movieStaffResponse(kinopoiskId)
        while (!movieStaffIsLoaded) {
            delay(100)
        }
        movieStaffIsLoaded = false
        return movieStaff.value
    }

    private fun movieImageResponse(
        kinopoiskId: Int,
        page: Int,
        type: MovieImageType
    ) {
        LoadMovieDataRetrofit.loadMovieDataApi.loadMovieImages(
            kinopoiskId = kinopoiskId, page = page, type = type.type
        ).enqueue(object : Callback<MovieImageListDto> {
            override fun onResponse(
                call: Call<MovieImageListDto>,
                response: Response<MovieImageListDto>
            ) {
                val responseCode = response.code()
                val responseBody = response.body()
                movieImageList.value = responseBody
                Log.d(RETROFIT_TAG, "Response code, load movie images: $responseCode")
                movieImageListIsLoaded = true
            }

            override fun onFailure(call: Call<MovieImageListDto>, t: Throwable) {
                Log.e(RETROFIT_TAG, "${t.message}")
                movieImageList.value = null
                movieImageListIsLoaded = true
            }

        })
    }

    override suspend fun loadMovieImage(
        kinopoiskId: Int,
        page: Int,
        type: MovieImageType
    ): MovieImageList? {
        movieImageResponse(kinopoiskId, page, type)
        while (!movieImageListIsLoaded) {
            delay(100)
        }
        movieImageListIsLoaded = false
        return movieImageList.value
    }

    private fun similarMoviesResponse(kinopoiskId: Int) {
        LoadMovieDataRetrofit.loadMovieDataApi.loadSimilarMovies(kinopoiskId)
            .enqueue(object : Callback<MovieListDto> {
                override fun onResponse(
                    call: Call<MovieListDto>,
                    response: Response<MovieListDto>
                ) {
                    val responseCode = response.code()
                    val responseBody = response.body()
                    similarMovies.value = responseBody
                    Log.d(RETROFIT_TAG, "Response code, similar movies: $responseCode")
                    similarMoviesIsLoaded = true
                }

                override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                    Log.e(RETROFIT_TAG, "${t.message}")
                    similarMovies.value = null
                    similarMoviesIsLoaded = true
                }
            })
    }

    override suspend fun loadSimilarMovies(kinopoiskId: Int): MovieList? {
        similarMoviesResponse(kinopoiskId)
        while (!similarMoviesIsLoaded) {
            delay(100)
        }
        similarMoviesIsLoaded = false
        return similarMovies.value
    }

    companion object {
        private const val RETROFIT_TAG = "RETROFIT"
        private const val RETROFIT_EMPTY_BODY = "Response body is empty"
    }
}