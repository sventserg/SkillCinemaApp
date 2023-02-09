package com.example.skillcinema.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.data.database.DBUserMovieList

data class DBUserMovieListImp(
    @Embedded
    override val savedMovieList: DBMovieListImp,
    @Relation(
        parentColumn = "movie_list_name",
        entityColumn = "id",
        associateBy = Junction(
            DBMovieInMovieListImp::class,
            parentColumn = "list_name",
            entityColumn = "movie_id"
        )
    )
    override val movies: List<DBMovieImp>
) : DBUserMovieList {
    override fun checkMovie(movie: Movie): Boolean {
        var check = false
        movies.forEach {
            if (it.id == movie.id()) check = true
        }
        return check
    }
}
