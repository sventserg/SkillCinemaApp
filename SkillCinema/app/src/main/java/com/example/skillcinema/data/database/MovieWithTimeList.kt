package com.example.skillcinema.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithTimeList(
    @Embedded
    val collection: DBMovieListImp,

    @Relation(
        parentColumn = "movie_list_name",
        entityColumn = "time",
        associateBy = Junction(
            DBMovieInMovieListImp::class,
            parentColumn = "list_name",
            entityColumn = "time"
        )
    )
    val movieList: List<DBMovieWithTime>
)
