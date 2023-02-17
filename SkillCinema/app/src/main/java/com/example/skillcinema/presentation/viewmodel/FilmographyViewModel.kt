package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.skillcinema.data.*
import com.example.skillcinema.domain.GetSortedMovieListUseCase
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.Person
import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.presentation.adapter.movieList.LoadMoviePagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilmographyViewModel(
    private val getSortedMovieList: GetSortedMovieListUseCase,
    private val loadMovieData: LoadMovieDataUseCase
) : ViewModel() {

    private val _filmography = MutableStateFlow<Map<String, List<PersonMovie>>?>(null)
    val filmography = _filmography.asStateFlow()
    private val personProfessionKeyList = PersonProfessionKeyList().personProfessionKeyList

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        enablePlaceholders = true
    )

    fun getFilmography(person: Person) {
        val personMovieList = person.films
        val map = mutableMapOf<String, List<PersonMovie>>()

        personProfessionKeyList.forEach {
            val movieList = getSortedMovieList.getSortedMovieList(personMovieList, it)
            if (movieList.isNotEmpty()) map[it.key] = movieList
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

    fun getPagingMovies(movieList: List<PersonMovie>) = Pager(
        config = pagingConfig,
        initialKey = 0,
        pagingSourceFactory = {
            LoadMoviePagingSource(
                loadMovieData = loadMovieData,
                movieList
            )
        }
    ).flow.cachedIn(viewModelScope)
}