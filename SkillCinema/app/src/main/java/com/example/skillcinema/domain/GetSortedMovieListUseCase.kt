package com.example.skillcinema.domain

import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.entity.PersonProfessionKey

class GetSortedMovieListUseCase {

    fun getSortedMovieList(
        movieList: List<PersonMovie>,
        personProfessionKey: PersonProfessionKey
    ): MutableList<PersonMovie> {
        val sortedMovieList = mutableListOf<PersonMovie>()
        movieList.forEach {
            if (it.professionKey == personProfessionKey.key) {
                sortedMovieList.add(it)
            }
        }
        return sortedMovieList
    }

    fun getBestMoviesList(movieList: List<PersonMovie>): Set<Int> {

        val bestMoviesList = mutableSetOf<Int>()
        val initialList = mutableListOf<PersonMovie>()
        movieList.forEach {
            initialList.add(it)
        }
        repeat(initialList.size) { _ ->
            val movie = findBestMovie(initialList)
            initialList.remove(movie)
            bestMoviesList.add(movie.filmId)
        }
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