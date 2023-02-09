package com.example.skillcinema.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class DBMovieWithTime(
    @PrimaryKey
    val primaryID: Int,

    @Embedded
    val movie: DBMovieImp,

    @ColumnInfo(name = "time")
    val time: Long
)