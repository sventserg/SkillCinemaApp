package com.example.skillcinema.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.skillcinema.data.MovieImageTypeImp
import com.example.skillcinema.data.MovieImageTypeList
import com.example.skillcinema.domain.GetFilteredStaff
import com.example.skillcinema.domain.LoadMovieData
import com.example.skillcinema.domain.LoadSeasons
import com.example.skillcinema.domain.SeriesCalculation
import com.example.skillcinema.entity.*
import com.example.skillcinema.presentation.MOVIE_TYPE_MINI_SERIES
import com.example.skillcinema.presentation.MOVIE_TYPE_TV_SERIES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviePageVM(
    private val loadMovieData: LoadMovieData,
    private val getFilteredStaff: GetFilteredStaff,
    private val loadSeasons: LoadSeasons,
    private val seriesCalculation: SeriesCalculation
) : ViewModel() {

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _actors = MutableStateFlow<List<Staff>?>(null)
    val actors = _actors.asStateFlow()


    private val _workers = MutableStateFlow<List<Staff>?>(null)
    val workers = _workers.asStateFlow()

    private val _gallery = MutableStateFlow<List<MovieImage>?>(null)
    val gallery = _gallery.asStateFlow()

    private val _gallerySize = MutableStateFlow(0)
    val gallerySize = _gallerySize.asStateFlow()

    private val _similarMovies = MutableStateFlow<List<Movie>?>(null)
    val similarMovies = _similarMovies.asStateFlow()

    private val _galleryTypeNumbers = MutableStateFlow<Map<String, Int>>(emptyMap())
    val galleryTypeNumbers = _galleryTypeNumbers.asStateFlow()

    private val _isItSeries = MutableStateFlow(false)
    val isItSeries = _isItSeries.asStateFlow()

    private val _seasons = MutableStateFlow<Seasons?>(null)
    val seasons = _seasons.asStateFlow()

    private val _episodesNumber = MutableStateFlow<String?>(null)
    val episodesNumber = _episodesNumber.asStateFlow()

    suspend fun setMovie(movieID: Int) {
        if (movieID != -1) loadMovie(movieID)
        val movie = _selectedMovie.value
        if (movie != null) {
            loadMovieInformation(movie)
            isItSeries(movie)
            if (_isItSeries.value) loadSeasons(movie)
        }
    }

    private suspend fun loadMovie(id: Int) {
        val movie = loadMovieData.loadMovie(id)
        Log.d("ViewModel", "load movie, movie: ${movie?.id()}")
        _selectedMovie.value = movie
    }

    private suspend fun loadMovieInformation(movie: Movie) {
        val movieID = movie.id()
//        if (movieID != null) {
//            _selectedMovie.value = loadMovieData.loadMovie(movieID)
        val movieStaff = loadMovieData.loadMovieStaff(movieID)
        if (movieStaff != null) {
            _actors.value = getFilteredStaff.getActors(movieStaff)
            _workers.value = getFilteredStaff.getWorkers(movieStaff)
        }
        _gallery.value = loadMovieData.loadMovieImage(
            movieID, page = 1,
            MovieImageTypeImp.STILL
        )?.imageList
        _similarMovies.value = loadMovieData.loadSimilarMovies(movieID)?.movieList

        val movieImageTypeList = MovieImageTypeList().movieImageTypeList
        var imageNumber = 0
        val galleryTypeNumbersMap = mutableMapOf<String, Int>()
        movieImageTypeList.forEach {
            val number = loadMovieData.loadMovieImage(movieID, 1, it)?.total
            if (number != null) {
                galleryTypeNumbersMap[it.type] = number
                imageNumber += number
            }
        }
        _gallerySize.value = imageNumber
        _galleryTypeNumbers.value = galleryTypeNumbersMap
//        }
    }

    private fun isItSeries(movie: Movie) {
        val type = movie.type
        _isItSeries.value = type == MOVIE_TYPE_TV_SERIES || type == MOVIE_TYPE_MINI_SERIES
    }

    private suspend fun loadSeasons(movie: Movie) {
        val movieID = movie.id()
        val seasons = loadSeasons.loadSeasons(movieID)
        if (seasons != null) {
            _seasons.value = seasons
            _episodesNumber.value = seriesCalculation.getEpisodesNumber(seasons)
        }
    }

}