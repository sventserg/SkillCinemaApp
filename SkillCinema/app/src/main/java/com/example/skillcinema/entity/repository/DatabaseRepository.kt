package com.example.skillcinema.entity.repository

import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.database.DBMovie
import com.example.skillcinema.entity.database.DBMovieList
import com.example.skillcinema.entity.database.DBUserMovieList

interface DatabaseRepository {

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(id: Int)

    suspend fun getSavedMovieLists(): List<DBUserMovieList>

    suspend fun getSavedMovieList(name: String): List<DBMovie>

    suspend fun insertMovieList(movieList: DBMovieList)

    suspend fun insertMovieToMovieList(movieID: Int, listName: String, time: Long)

    suspend fun deleteMovieList(name: String)

    suspend fun deleteMovieListMovies(listName: String)

    suspend fun deleteMovieFromMovieList(movieID: Int, listName: String)

    suspend fun clearCollection(name: String)
}