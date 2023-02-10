package com.example.skillcinema.data.database

import androidx.room.*
import com.example.skillcinema.entity.database.DBMovieList

@Entity(tableName = "saved_movie_list")
data class DBMovieListImp(
    @PrimaryKey
    @ColumnInfo(name = "movie_list_name")
    override val listName: String
) : DBMovieList