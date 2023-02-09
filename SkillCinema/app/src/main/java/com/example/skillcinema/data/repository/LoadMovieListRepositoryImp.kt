package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.dto.MovieListDto
import com.example.skillcinema.data.dto.TopMovieListDto
import com.example.skillcinema.data.retrofit.LoadMovieListRetrofit
import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.MovieList
import com.example.skillcinema.entity.data.repository.LoadMovieListRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadMovieListRepositoryImp : LoadMovieListRepository {

    private val premieres = MutableStateFlow<MovieList?>(null)
    private val popularMovies = MutableStateFlow<MovieList?>(null)
    private val bestMovies = MutableStateFlow<MovieList?>(null)
    private val tvSeries = MutableStateFlow<MovieList?>(null)
    private val miniSeries = MutableStateFlow<MovieList?>(null)
    private val filteredMovies = MutableStateFlow<MovieList?>(null)

    private var premieresIsLoaded = false
    private var popularMoviesIsLoaded = false
    private var bestMoviesIsLoaded = false
    private var tvSeriesIsLoaded = false
    private var miniSeriesIsLoaded = false
    private var filteredMoviesIsLoaded = false

    private fun premieresResponse(year: Int, month: String) {
        LoadMovieListRetrofit.loadMovieListApi.loadPremieres(
            year = year,
            month = month
        ).enqueue(object : Callback<MovieListDto> {
            override fun onResponse(call: Call<MovieListDto>, response: Response<MovieListDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                premieres.value = responseBody
                Log.d(RETROFIT_TAG, "Response code, load premieres: $responseCode")
                premieresIsLoaded = true
            }

            override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                Log.e(RETROFIT_TAG, "${t.message}")
                premieres.value = null
                premieresIsLoaded = true
            }
        })
    }

    override suspend fun loadPremieres(year: Int, month: String): MovieList? {
        premieresResponse(year, month)
        while (!premieresIsLoaded) {
            delay(100)
        }
        premieresIsLoaded = false
        return premieres.value
    }

    private fun popularMoviesResponse(page: Int) {
        LoadMovieListRetrofit.loadMovieListApi.loadPopularMovies(
            page = page
        ).enqueue(object : Callback<TopMovieListDto> {
            override fun onResponse(
                call: Call<TopMovieListDto>,
                response: Response<TopMovieListDto>
            ) {
                val responseCode = response.code()
                val responseBody = response.body()
                popularMovies.value = responseBody
                Log.d(RETROFIT_TAG, "Response code, load popular movies: $responseCode")
                popularMoviesIsLoaded = true
            }

            override fun onFailure(call: Call<TopMovieListDto>, t: Throwable) {
                Log.e(RETROFIT_TAG, "${t.message}")
                popularMovies.value = null
                popularMoviesIsLoaded = true
            }

        })
    }

    override suspend fun loadPopularMovies(page: Int): MovieList? {
        popularMoviesResponse(page)
        while (!popularMoviesIsLoaded) {
            delay(100)
        }
        popularMoviesIsLoaded = false
        return popularMovies.value
    }

    private fun bestMoviesResponse(page: Int) {
        LoadMovieListRetrofit.loadMovieListApi.loadBestMovies(
            page = page
        ).enqueue(object : Callback<TopMovieListDto> {
            override fun onResponse(
                call: Call<TopMovieListDto>,
                response: Response<TopMovieListDto>
            ) {
                val responseCode = response.code()
                val responseBody = response.body()
                bestMovies.value = responseBody
                Log.d(RETROFIT_TAG, "Response code, load best movies: $responseCode")
                bestMoviesIsLoaded = true
            }

            override fun onFailure(call: Call<TopMovieListDto>, t: Throwable) {
                Log.e(RETROFIT_TAG, "${t.message}")
                bestMovies.value = null
                bestMoviesIsLoaded = true
            }
        })
    }

    override suspend fun loadBestMovies(page: Int): MovieList? {
        bestMoviesResponse(page)
        while (!bestMoviesIsLoaded) {
            delay(100)
        }
        bestMoviesIsLoaded = false
        return bestMovies.value
    }

    private fun tvSeriesResponse(page: Int) {
        LoadMovieListRetrofit.loadMovieListApi.loadTVSeries(
            page = page
        ).enqueue(object : Callback<MovieListDto> {
            override fun onResponse(call: Call<MovieListDto>, response: Response<MovieListDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                tvSeries.value = responseBody
                Log.d(RETROFIT_TAG, "Response code, load tv series: $responseCode")
                tvSeriesIsLoaded = true
            }

            override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                Log.e(RETROFIT_TAG, "${t.message}")
                tvSeries.value = null
                tvSeriesIsLoaded = true
            }
        })
    }

    override suspend fun loadTVSeries(page: Int): MovieList? {
        tvSeriesResponse(page)
        while (!tvSeriesIsLoaded) {
            delay(100)
        }
        tvSeriesIsLoaded = false
        return tvSeries.value
    }

    private fun miniSeriesResponse(page: Int) {
        LoadMovieListRetrofit.loadMovieListApi.loadMiniSeries(
            page = page
        ).enqueue(object : Callback<MovieListDto> {
            override fun onResponse(call: Call<MovieListDto>, response: Response<MovieListDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                miniSeries.value = responseBody
                Log.d(RETROFIT_TAG, "Response code, load mini series: $responseCode")
                miniSeriesIsLoaded = true
            }

            override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                Log.e(RETROFIT_TAG, "${t.message}")
                miniSeries.value = null
                miniSeriesIsLoaded = true
            }
        })
    }

    override suspend fun loadMiniSeries(page: Int): MovieList? {
        miniSeriesResponse(page)
        while (!miniSeriesIsLoaded) {
            delay(100)
        }
        miniSeriesIsLoaded = false
        return miniSeries.value
    }

    private fun filteredMoviesResponse(
        country: Country?,
        genre: Genre?,
        page: Int
    ) {
        if (country != null && genre != null) {
            LoadMovieListRetrofit.loadMovieListApi.loadFilteredMovies(
                countryId = country.id,
                genreId = genre.id,
                page = page
            ).enqueue(object : Callback<MovieListDto> {
                override fun onResponse(
                    call: Call<MovieListDto>,
                    response: Response<MovieListDto>
                ) {
                    val responseCode = response.code()
                    val responseBody = response.body()
                    filteredMovies.value = responseBody
                    Log.d(RETROFIT_TAG, "Response code, load filtered movies: $responseCode")
                    filteredMoviesIsLoaded = true
                }

                override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                    Log.e(RETROFIT_TAG, "${t.message}")
                    filteredMovies.value = null
                    filteredMoviesIsLoaded = true
                }
            })
        } else {
            miniSeriesIsLoaded = true
        }
    }

    override suspend fun loadFilteredMovies(
        country: Country?,
        genre: Genre?,
        page: Int
    ): MovieList? {
        filteredMoviesResponse(country, genre, page)
        while (!filteredMoviesIsLoaded) {
            delay(100)
        }
        filteredMoviesIsLoaded = false
        return filteredMovies.value
    }

    companion object {
        private const val RETROFIT_TAG = "RETROFIT"
    }
}