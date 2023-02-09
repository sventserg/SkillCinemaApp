package com.example.skillcinema.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "saved_movie_list_movie",
    primaryKeys = ["list_name", "movie_id"]
)
data class DBMovieInMovieListImp(

    @ColumnInfo(name = "list_name")
    val savedMovieListName: String,
    @ColumnInfo(name = "movie_id")
    val movieID: Int,
    @ColumnInfo(name = "time")
    val time: Long
)
