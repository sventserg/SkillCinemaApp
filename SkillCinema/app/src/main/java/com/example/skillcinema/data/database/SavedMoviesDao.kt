package com.example.skillcinema.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.skillcinema.entity.data.database.DBMovie

@Dao
interface SavedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(dbMovie: DBMovieImp)

    @Query("DELETE FROM saved_movies WHERE id = :id")
    suspend fun deleteMovie(id: Int)

    @Transaction
    @Query("SELECT * FROM saved_movie_list")
    suspend fun getSavedMovieLists(): List<DBUserMovieListImp>

    @Query("SELECT * FROM saved_movies JOIN saved_movie_list_movie ON saved_movies.id = saved_movie_list_movie.movie_id WHERE list_name LIKE :name ORDER BY time")
    suspend fun getSavedMovieList(name: String): List<DBMovieImp>

    @Query("INSERT OR REPLACE INTO saved_movie_list (movie_list_name) VALUES (:name)")
    suspend fun insertMovieList(name: String)

    @Query("INSERT OR REPLACE INTO saved_movie_list_movie (list_name, movie_id, time) VALUES(:listName, :movieID, :time)")
    suspend fun insertMovieToMovieList(movieID: Int, listName: String, time: Long)

    @Query("DELETE FROM saved_movie_list WHERE movie_list_name = :name")
    suspend fun deleteMovieList(name: String)

    @Query("DELETE FROM saved_movie_list_movie WHERE list_name = :name")
    suspend fun deleteMovieListMovies(name: String)

    @Update
    suspend fun updateMovieList(savedMovieList: DBMovieListImp)

    @Query("DELETE FROM saved_movie_list_movie WHERE movie_id = :movieID AND list_name = :movieListName")
    suspend fun deleteMovieFromMovieList(movieID: Int, movieListName: String)

    @Transaction
    @Query("SELECT * FROM saved_movie_list_movie WHERE list_name LIKE :name ORDER BY time")
    suspend fun getCollection(name: String): List<DBMovieInMovieListImp>

    @Query("DELETE FROM saved_movie_list_movie WHERE list_name LIKE :name")
    suspend fun clearCollection(name: String)

    @Query("SELECT * FROM saved_movie_list_movie WHERE movie_id LIKE :id")
    suspend fun isMovieInCollection(id: Int): List<DBMovieInMovieListImp>

    @Query("SELECT * FROM saved_movies")
    suspend fun getSavedMovies(): List<DBMovieImp>
}