package com.example.skillcinema.entity.database

import com.example.skillcinema.entity.Movie

interface DBUserMovieList {
    val savedMovieList: DBMovieList
    val movies: List<DBMovie>
    fun checkMovie(movie: Movie): Boolean
}