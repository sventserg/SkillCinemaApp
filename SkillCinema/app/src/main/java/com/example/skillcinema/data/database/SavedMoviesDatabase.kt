package com.example.skillcinema.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DBMovieImp::class,
        DBMovieListImp::class,
        DBMovieInMovieListImp::class
    ],
    version = 1
)
abstract class SavedMoviesDatabase : RoomDatabase() {
    abstract fun savedMoviesDao(): SavedMoviesDao
}