package com.example.skillcinema.entity.repository

import com.example.skillcinema.entity.MovieList
import com.example.skillcinema.entity.PersonByNameList

interface SearchRepository {
    suspend fun searchMovieByCountryAndGenre(
        countries: Int, genres: Int,
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int,
        yearFrom: Int, yearTo: Int,
        keyword: String, page: Int
    ): MovieList?

    suspend fun searchMovieByCountry(
        countries: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList?

    suspend fun searchMovieByGenre(
        genres: Int, order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList?

    suspend fun searchMovie(
        order: String, type: String,
        ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, keyword: String, page: Int
    ): MovieList?

    suspend fun searchPersonByName(name: String, page: Int): PersonByNameList?
}