package com.example.skillcinema.domain

import android.util.Log
import com.example.skillcinema.entity.MovieList
import com.example.skillcinema.entity.PersonByNameList
import com.example.skillcinema.entity.data.repository.SearchRepository

class Search(
    private val repository: SearchRepository
) {
    suspend fun searchMovieByCountryAndGenre(
        countries: Int, genres: Int,
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int,
        yearFrom: Int, yearTo: Int,
        keyword: String, page: Int
    ): MovieList? {
        return repository.searchMovieByCountryAndGenre(
            countries, genres, order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        )
    }

    suspend fun searchMovieByCountry(
        countries: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList? {
        return repository.searchMovieByCountry(
            countries, order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        )
    }

    suspend fun searchMovieByGenre(
        genres: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList? {
        return repository.searchMovieByGenre(
            genres, order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        )
    }

    suspend fun searchMovie(
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList? {
        Log.d("UseCase", "Start searching")
        return repository.searchMovie(
            order, type, ratingFrom,
            ratingTo, yearFrom, yearTo, keyword, page
        )
    }

    suspend fun searchPersonByName(name: String, page: Int): PersonByNameList? {
        return repository.searchPersonByName(name, page)
    }
}