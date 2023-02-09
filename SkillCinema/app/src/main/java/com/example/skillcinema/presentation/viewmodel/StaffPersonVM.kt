package com.example.skillcinema.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.skillcinema.R
import com.example.skillcinema.domain.LoadMovieData
import com.example.skillcinema.domain.LoadPerson
import com.example.skillcinema.domain.MovieRatingSorter
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.Person
import com.example.skillcinema.entity.PersonMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.min

class StaffPersonVM(
    private val loadPerson: LoadPerson,
    private val loadMovieData: LoadMovieData,
    private val movieRatingSorter: MovieRatingSorter
) : ViewModel() {

    private var _person = MutableStateFlow<Person?>(null)
    val person = _person.asStateFlow()
    private val _moviesQuantity = MutableStateFlow<String>("0 фильмов")
    val moviesQuantity = _moviesQuantity.asStateFlow()
    private var _bestPersonMovies = MutableStateFlow<List<Movie>?>(null)
    val bestPersonMovies = _bestPersonMovies.asStateFlow()

    suspend fun loadPerson(personId: Int, context: Context) {
        val person = loadPerson.loadPerson(personId)
        _person.value = person

        if (person != null) {
            val moviesQuantity = person.films.size
            _moviesQuantity.value =
                context.resources.getQuantityString(
                    R.plurals.movies,
                    moviesQuantity,
                    moviesQuantity
                )

            val bestPersonMovies = getBestMoviesList(person.films)
            loadPersonMoviesList(bestPersonMovies)
        }
    }

    private suspend fun loadPersonMoviesList(personMovies: List<Int>) {
        val moviesList = mutableListOf<Movie>()
        val size = min(20, personMovies.size)
        for (i in 0 until size) {
            val movie = loadMovieData.loadMovie(personMovies[i])
            if (movie != null) {
                moviesList.add(movie)
            }
        }
        _bestPersonMovies.value = moviesList
    }

    private fun getBestMoviesList(movieList: List<PersonMovie>): List<Int> {
        return movieRatingSorter.getBestMoviesList(movieList).toList()
    }

}