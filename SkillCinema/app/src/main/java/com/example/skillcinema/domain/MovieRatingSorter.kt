package com.example.skillcinema.domain

import android.util.Log
import com.example.skillcinema.entity.PersonMovie
import kotlin.math.min

class MovieRatingSorter {
    fun getBestMoviesList(movieList: List<PersonMovie>): Set<Int> {

        val bestMoviesList = mutableSetOf<Int>()
        val initialList = mutableListOf<PersonMovie>()
        movieList.forEach {
            initialList.add(it)
        }
        Log.d("RatingSorter", "ListSize: ${initialList.size}")
        repeat(initialList.size) { _ ->
            val movie = findBestMovie(initialList)
            initialList.remove(movie)
            bestMoviesList.add(movie.filmId)
        }

        Log.d("RatingSorter", "ListSize: ${bestMoviesList.size}")
        return bestMoviesList
    }

    private fun findBestMovie(
        movieList: List<PersonMovie>
    ): PersonMovie {

        var movie = movieList[0]

        for (i in movieList.indices) {
            val rating = movieList[i].rating
            if (rating != null && !rating.contains("%")) {
                movie = movieList[i]
                break
            }
        }

        movieList.forEach {
            val currentRating = movie.rating?.toDouble()
            val rating = it.rating?.toDouble()
            if (currentRating != null && rating != null) {
                if (rating > currentRating) {
                    movie = it
                }
            }
        }
        return movie
    }
}