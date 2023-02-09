package com.example.skillcinema.data.database

import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.data.database.DBMovie
import com.example.skillcinema.entity.data.database.DBMovieList
import com.example.skillcinema.entity.data.database.DBUserMovieList
import com.example.skillcinema.entity.data.database.DatabaseRepository

class DatabaseRepositoryImp(private val dbStorage: DatabaseStorage) : DatabaseRepository {

    override suspend fun insertMovie(movie: Movie) {
        val dbMovie = DBMovieImp(
            id = movie.id(),
            name = movie.name(),
            genre = movie.genresText(),
            posterUrl = movie.posterUrl,
            rating = movie.rating()
        )
        dbStorage.database.savedMoviesDao().insertMovie(dbMovie)
    }

    override suspend fun deleteMovie(id: Int) {
        dbStorage.database.savedMoviesDao().deleteMovie(id)
    }

    override suspend fun getSavedMovieLists(): List<DBUserMovieList> {
        return dbStorage.database.savedMoviesDao().getSavedMovieLists()
    }

    override suspend fun getSavedMovieList(name: String): List<DBMovie> {
        return dbStorage.database.savedMoviesDao().getSavedMovieList(name)
    }

    override suspend fun insertMovieList(movieList: DBMovieList) {
        dbStorage.database.savedMoviesDao().insertMovieList(movieList.listName)
    }

    override suspend fun insertMovieToMovieList(movieID: Int, listName: String, time: Long) {
        dbStorage.database.savedMoviesDao().insertMovieToMovieList(movieID, listName, time)
    }

    override suspend fun deleteMovieList(name: String) {
        dbStorage.database.savedMoviesDao().deleteMovieList(name)
    }

    override suspend fun deleteMovieListMovies(listName: String) {
        dbStorage.database.savedMoviesDao().deleteMovieListMovies(listName)
    }

    override suspend fun deleteMovieFromMovieList(movieID: Int, listName: String) {
        dbStorage.database.savedMoviesDao().deleteMovieFromMovieList(movieID, listName)
    }

    override suspend fun clearCollection(name: String) {
        dbStorage.database.savedMoviesDao().clearCollection(name)
    }

}