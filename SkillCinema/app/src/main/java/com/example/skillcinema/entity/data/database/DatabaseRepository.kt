package com.example.skillcinema.entity.data.database

import com.example.skillcinema.data.database.DBMovieImp
import com.example.skillcinema.entity.Movie

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