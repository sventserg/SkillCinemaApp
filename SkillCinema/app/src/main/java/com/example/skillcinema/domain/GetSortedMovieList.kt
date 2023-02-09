package com.example.skillcinema.domain

import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.entity.PersonProfessionKey

class GetSortedMovieList {
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
}