package com.example.skillcinema.presentation

import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.Movie

sealed class MovieListType

data class Premieres(
    val year: Int,
    val month: String
) : MovieListType()

object PopularMovies : MovieListType()
object BestMovies : MovieListType()
object TVSeries : MovieListType()
object MiniSeries : MovieListType()

data class FilteredMovies(
    val country: Country?,
    val genre: Genre?
) : MovieListType()

data class MovieCollectionType(
    val movieList: List<Movie>,
    val name: String
) : MovieListType()
