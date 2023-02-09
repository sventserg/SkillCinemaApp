package com.example.skillcinema.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillcinema.entity.data.database.DBMovie

@Entity(tableName = "saved_movies")
data class DBMovieImp(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    override val id: Int,
    @ColumnInfo(name = "movie_name")
    override val name: String,
    @ColumnInfo(name = "movie_genre")
    override val genre: String,
    @ColumnInfo(name = "movie_poster_url")
    override val posterUrl: String,
    @ColumnInfo(name = "movie_rating")
    override val rating: String?
) : DBMovie
