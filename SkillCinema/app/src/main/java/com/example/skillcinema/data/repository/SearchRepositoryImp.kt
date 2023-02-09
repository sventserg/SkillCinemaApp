package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.dto.MovieListDto
import com.example.skillcinema.data.dto.PersonByNameListDto
import com.example.skillcinema.data.dto.SeasonsDto
import com.example.skillcinema.data.retrofit.LoadSeasonsRetrofit
import com.example.skillcinema.data.retrofit.SearchRetrofit
import com.example.skillcinema.entity.MovieList
import com.example.skillcinema.entity.PersonByNameList
import com.example.skillcinema.entity.data.repository.SearchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepositoryImp : SearchRepository {

    private val searchMovieByCountryAndGenreResult = MutableStateFlow<MovieList?>(null)
    private val searchMovieByCountryResult = MutableStateFlow<MovieList?>(null)
    private val searchMovieByGenreResult = MutableStateFlow<MovieList?>(null)
    private val searchMovieResult = MutableStateFlow<MovieList?>(null)
    private val searchPersonByNameResult = MutableStateFlow<PersonByNameList?>(null)

    private var searchMovieByCountryAndGenreResultIsLoaded = false
    private var searchMovieByCountryResultIsLoaded = false
    private var searchMovieByGenreResultIsLoaded = false
    private var searchMovieResultIsLoaded = false
    private var searchPersonByNameResultIsLoaded = false


    private fun searchMovieByCountryAndGenreResponse(
        countries: Int, genres: Int,
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int,
        yearFrom: Int, yearTo: Int,
        keyword: String, page: Int
    ) {
        SearchRetrofit.searchApi.searchMovieByCountryAndGenre(
            countries, genres, order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        ).enqueue(object : Callback<MovieListDto> {
            override fun onResponse(call: Call<MovieListDto>, response: Response<MovieListDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                searchMovieByCountryAndGenreResult.value = responseBody
                Log.d(
                    RETROFIT_TAG,
                    "Response code, search movie by country and genre: $responseCode"
                )
                searchMovieByCountryAndGenreResultIsLoaded = true
            }

            override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                Log.d(RETROFIT_TAG, "${t.message}")
                searchMovieByCountryAndGenreResult.value = null
                searchMovieByCountryAndGenreResultIsLoaded = true
            }
        })
    }

    override suspend fun searchMovieByCountryAndGenre(
        countries: Int, genres: Int,
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int,
        yearFrom: Int, yearTo: Int,
        keyword: String, page: Int
    ): MovieList? {
        searchMovieByCountryAndGenreResponse(
            countries, genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, keyword, page
        )
        while (!searchMovieByCountryAndGenreResultIsLoaded) {
            delay(100)
        }
        searchMovieByCountryAndGenreResultIsLoaded = false
        return searchMovieByCountryAndGenreResult.value
    }


    private fun searchMovieByCountryResponse(
        countries: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ) {
        SearchRetrofit.searchApi.searchMovieByCountry(
            countries, order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        ).enqueue(object : Callback<MovieListDto> {
            override fun onResponse(call: Call<MovieListDto>, response: Response<MovieListDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                searchMovieByCountryResult.value = responseBody
                Log.d(
                    RETROFIT_TAG,
                    "Response code, search movie by country: $responseCode"
                )
                searchMovieByCountryResultIsLoaded = true
            }

            override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                Log.d(RETROFIT_TAG, "${t.message}")
                searchMovieByCountryResult.value = null
                searchMovieByCountryResultIsLoaded = true
            }
        })
    }

    override suspend fun searchMovieByCountry(
        countries: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList? {
        searchMovieByCountryResponse(
            countries, order, type,
            ratingFrom, ratingTo, yearFrom,
            yearTo, keyword, page
        )
        while (!searchMovieByCountryResultIsLoaded) {
            delay(100)
        }
        searchMovieByCountryResultIsLoaded = false
        return searchMovieByCountryResult.value
    }


    private fun searchMovieByGenreResponse(
        genres: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ) {
        SearchRetrofit.searchApi.searchMovieByGenre(
            genres, order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        ).enqueue(object : Callback<MovieListDto> {
            override fun onResponse(call: Call<MovieListDto>, response: Response<MovieListDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                searchMovieByGenreResult.value = responseBody
                Log.d(
                    RETROFIT_TAG,
                    "Response code, search movie by genre: $responseCode"
                )
                searchMovieByGenreResultIsLoaded = true
            }

            override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                Log.d(RETROFIT_TAG, "${t.message}")
                searchMovieByGenreResult.value = null
                searchMovieByGenreResultIsLoaded = true
            }
        })
    }

    override suspend fun searchMovieByGenre(
        genres: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList? {
        searchMovieByGenreResponse(
            genres, order, type,
            ratingFrom, ratingTo, yearFrom,
            yearTo, keyword, page
        )
        while (!searchMovieByGenreResultIsLoaded) {
            delay(100)
        }
        searchMovieByGenreResultIsLoaded = false
        return searchMovieByGenreResult.value
    }


    private fun searchMovieResponse(
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ) {
        SearchRetrofit.searchApi.searchMovie(
            order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        ).enqueue(object : Callback<MovieListDto> {
            override fun onResponse(call: Call<MovieListDto>, response: Response<MovieListDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                searchMovieResult.value = responseBody
                Log.d(
                    RETROFIT_TAG,
                    "Response code, search movie: $responseCode"
                )
                searchMovieResultIsLoaded = true
            }

            override fun onFailure(call: Call<MovieListDto>, t: Throwable) {
                Log.d(RETROFIT_TAG, "${t.message}")
                searchMovieResult.value = null
                searchMovieResultIsLoaded = true
            }
        })
    }

    override suspend fun searchMovie(
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList? {
        searchMovieResponse(order, type, ratingFrom, ratingTo, yearFrom, yearTo, keyword, page)
        while (!searchMovieResultIsLoaded) {
            delay(100)
        }
        searchMovieResultIsLoaded = false
        return searchMovieResult.value
    }


    private fun searchPersonByNameResponse(name: String, page: Int) {
        SearchRetrofit.searchApi.searchPersonByName(name, page)
            .enqueue(object : Callback<PersonByNameListDto> {
                override fun onResponse(
                    call: Call<PersonByNameListDto>,
                    response: Response<PersonByNameListDto>
                ) {
                    val responseCode = response.code()
                    val responseBody = response.body()
                    searchPersonByNameResult.value = responseBody
                    Log.d(
                        RETROFIT_TAG,
                        "Response code, search person by name: $responseCode"
                    )
                    searchPersonByNameResultIsLoaded = true
                }

                override fun onFailure(call: Call<PersonByNameListDto>, t: Throwable) {
                    Log.d(RETROFIT_TAG, "${t.message}")
                    searchPersonByNameResult.value = null
                    searchPersonByNameResultIsLoaded = true
                }
            })
    }

    override suspend fun searchPersonByName(name: String, page: Int): PersonByNameList? {
        searchPersonByNameResponse(name, page)
        while (!searchPersonByNameResultIsLoaded) {
            delay(100)
        }
        searchPersonByNameResultIsLoaded = false
        return searchPersonByNameResult.value
    }

    companion object {
        private const val RETROFIT_TAG = "RETROFIT"
    }
}