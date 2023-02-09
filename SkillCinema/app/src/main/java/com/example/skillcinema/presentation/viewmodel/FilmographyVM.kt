package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skillcinema.data.*
import com.example.skillcinema.domain.GetSortedMovieList
import com.example.skillcinema.domain.LoadMovieData
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.Person
import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.entity.PersonProfessionKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilmographyVM(
    private val getSortedMovieList: GetSortedMovieList,
    private val loadMovieData: LoadMovieData
) : ViewModel() {

    private val _filmography = MutableStateFlow<Map<String, List<Movie>>?>(null)
    val filmography = _filmography.asStateFlow()
    private val personProfessionKeyList = PersonProfessionKeyList().personProfessionKeyList


    suspend fun getFilmography(person: Person) {
        val personMovieList = person.films
        val map = mutableMapOf<String, List<Movie>>()

        personProfessionKeyList.forEach {
            map[it.key] = getMovieList(getSortedMovieList.getSortedMovieList(personMovieList, it))
        }

        _filmography.value = map
    }

    private suspend fun getMovieList(personMovieList: List<PersonMovie>): List<Movie> {
        val movieList = mutableListOf<Movie>()
        personMovieList.forEach {
            val movie = loadMovieData.loadMovie(it.filmId)
            if (movie != null) movieList.add(movie)
        }
        return movieList
    }
}