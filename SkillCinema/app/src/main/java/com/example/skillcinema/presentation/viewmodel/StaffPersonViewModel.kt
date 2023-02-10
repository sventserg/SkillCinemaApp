package com.example.skillcinema.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.skillcinema.R
import com.example.skillcinema.domain.GetSortedMovieListUseCase
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.domain.LoadPersonUseCase
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.Person
import com.example.skillcinema.entity.PersonMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.min

class StaffPersonViewModel(
    private val loadPerson: LoadPersonUseCase,
    private val loadMovieData: LoadMovieDataUseCase,
    private val getSortedMovieListUseCase: GetSortedMovieListUseCase
) : ViewModel() {

    private var _person = MutableStateFlow<Person?>(null)
    val person = _person.asStateFlow()
    private val _moviesQuantity = MutableStateFlow<String>(ZERO_MOVIES)
    val moviesQuantity = _moviesQuantity.asStateFlow()
    private var _bestPersonMovies = MutableStateFlow<List<Movie>?>(null)
    val bestPersonMovies = _bestPersonMovies.asStateFlow()

    suspend fun loadPerson(personId: Int, context: Context) {
        Log.d("PERSON", "personID from fragment, VM :${personId}")
        val newPerson = loadPerson.loadPerson(personId)
        Log.d("PERSON", "Person, VM :${newPerson?.personId}")
        _person.value = newPerson
        Log.d("PERSON", "person, VM State: ${_person.value?.personId}")
        if (newPerson != null) {
            val moviesQuantity = newPerson.films.size
            _moviesQuantity.value =
                context.resources.getQuantityString(
                    R.plurals.movies,
                    moviesQuantity,
                    moviesQuantity
                )

            val bestPersonMovies = getBestMoviesList(newPerson.films)
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
        return getSortedMovieListUseCase.getBestMoviesList(movieList).toList()
    }

    companion object {
        const val ZERO_MOVIES = "0 фильмов"
    }
}