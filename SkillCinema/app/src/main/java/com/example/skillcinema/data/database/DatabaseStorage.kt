package com.example.skillcinema.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.skillcinema.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DatabaseStorage(context: Context) {
    val database = Room.databaseBuilder(
        context,
        SavedMoviesDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    init {
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch {
            val collection = database.savedMoviesDao().getSavedMovieLists()
            val savedMovies = database.savedMoviesDao().getSavedMovies()
            if (collection.isEmpty()) {
                database.savedMoviesDao().insertMovieList(favorites.listName)
                database.savedMoviesDao().insertMovieList(wantToWatch.listName)
                database.savedMoviesDao().insertMovieList(viewed.listName)
                database.savedMoviesDao().insertMovieList(interested.listName)
            }
            savedMovies.forEach {
                if (database.savedMoviesDao().isMovieInCollection(it.id).isEmpty()) {
                    database.savedMoviesDao().deleteMovie(it.id)
                }
            }
            scope.cancel()
        }
    }

    private val favorites: DBMovieListImp =
        DBMovieListImp(FAVORITES_MOVIES_NAME)
    private val wantToWatch: DBMovieListImp =
        DBMovieListImp(WANT_TO_WATCH_MOVIES_NAME)
    private val viewed: DBMovieListImp = DBMovieListImp(VIEWED_MOVIES_NAME)
    private val interested: DBMovieListImp =
        DBMovieListImp(INTERESTED_MOVIES_NAME)
}