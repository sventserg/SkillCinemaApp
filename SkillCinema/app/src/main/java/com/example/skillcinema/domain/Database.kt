package com.example.skillcinema.domain

import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.data.database.DBMovie
import com.example.skillcinema.entity.data.database.DBMovieList
import com.example.skillcinema.entity.data.database.DBUserMovieList
import com.example.skillcinema.entity.data.database.DatabaseRepository

class Database(private val repository: DatabaseRepository) {

    suspend fun insertMovie(movie: Movie) {
        repository.insertMovie(movie)
    }

    suspend fun deleteMovie(id: Int) {
        repository.deleteMovie(id)
    }

    suspend fun getSavedMovieLists(): List<DBUserMovieList> {
        return repository.getSavedMovieLists()
    }

    suspend fun getSavedMovieList(name: String): List<DBMovie> {
        return repository.getSavedMovieList(name)
    }

    suspend fun insertMovieList(movieList: DBMovieList) {
        repository.insertMovieList(movieList)
    }

    suspend fun insertMovieToMovieList(movieID: Int, listName: String, time: Long) {
        repository.insertMovieToMovieList(movieID, listName, time)
    }

    suspend fun deleteMovieList(name: String) {
        repository.deleteMovieList(name)
    }

    suspend fun deleteMovieListMovies(listName: String) {
        repository.deleteMovieListMovies(listName)
    }

    suspend fun deleteMovieFromMovieList(movieID: Int, listName: String) {
        repository.deleteMovieFromMovieList(movieID, listName)
    }

    suspend fun clearCollection(name: String) {
        repository.clearCollection(name)
    }

}