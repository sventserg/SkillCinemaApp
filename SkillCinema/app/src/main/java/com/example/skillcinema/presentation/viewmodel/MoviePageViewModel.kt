package com.example.skillcinema.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.skillcinema.data.MovieImageTypeImp
import com.example.skillcinema.data.MovieImageTypeList
import com.example.skillcinema.domain.GetFilteredStaffUseCase
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.domain.LoadSeasonsUseCase
import com.example.skillcinema.domain.GetEpisodesNumberUseCase
import com.example.skillcinema.entity.*
import com.example.skillcinema.presentation.MOVIE_TYPE_MINI_SERIES
import com.example.skillcinema.presentation.MOVIE_TYPE_TV_SERIES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviePageViewModel(
    private val loadMovieData: LoadMovieDataUseCase,
    private val getFilteredStaff: GetFilteredStaffUseCase,
    private val loadSeasons: LoadSeasonsUseCase,
    private val seriesCalculation: GetEpisodesNumberUseCase
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
        val movieStaff = loadMovieData.loadMovieStaff(movieID)
        if (movieStaff != null) {
            _actors.value = getFilteredStaff.getActors(movieStaff)
            _workers.value = getFilteredStaff.getWorkers(movieStaff)
        }
        loadMovieImages(movieID)
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
    }

    private fun isItSeries(movie: Movie) {
        val type = movie.type
        _isItSeries.value = type == MOVIE_TYPE_TV_SERIES || type == MOVIE_TYPE_MINI_SERIES
    }

    private suspend fun loadMovieImages(movieID: Int) {
        val imageList = mutableListOf<MovieImage>()
        val imageStill = loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.STILL)
        Log.d("MPViewModel", "STILL loaded, size:${imageStill?.imageList?.size}")
        imageStill?.imageList?.forEach { imageList.add(it) }
        if (imageList.size < 20) {
            val imageShooting = loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.SHOOTING)
            Log.d("MPViewModel", "SHOOTING loaded, size:${imageShooting?.imageList?.size}")
            imageShooting?.imageList?.forEach { if (imageList.size < 20) imageList.add(it) }
        }
        if (imageList.size < 20) {
            val imagePoster = loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.POSTER)
            Log.d("MPViewModel", "POSTER loaded, size:${imagePoster?.imageList?.size}")
            imagePoster?.imageList?.forEach { if (imageList.size < 20)  imageList.add(it) }
        }

        if (imageList.size < 20) {
            val imageFanArt = loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.FAN_ART)
            Log.d("MPViewModel", "FAN_ART loaded, size:${imageFanArt?.imageList?.size}")
            imageFanArt?.imageList?.forEach { if (imageList.size < 20)  imageList.add(it) }
        }

        if (imageList.size < 20) {
            val imagePromo = loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.PROMO)
            Log.d("MPViewModel", "PROMO loaded, size:${imagePromo?.imageList?.size}")
            imagePromo?.imageList?.forEach { if (imageList.size < 20)  imageList.add(it) }
        }

        if (imageList.size < 20) {
            val imageConcept = loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.CONCEPT)
            Log.d("MPViewModel", "CONCEPT loaded, size:${imageConcept?.imageList?.size}")
            imageConcept?.imageList?.forEach { if (imageList.size < 20)  imageList.add(it) }
        }

        if (imageList.size < 20) {
            val imageWallpaper =
                loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.WALLPAPER)
            Log.d("MPViewModel", "WALLPAPER loaded, size:${imageWallpaper?.imageList?.size}")
            imageWallpaper?.imageList?.forEach { if (imageList.size < 20)  imageList.add(it) }
        }

        if (imageList.size < 20) {
            val imageCover = loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.COVER)
            Log.d("MPViewModel", "COVER loaded, size:${imageCover?.imageList?.size}")
            imageCover?.imageList?.forEach { if (imageList.size < 20)  imageList.add(it) }
        }

        if (imageList.size < 20) {
            val imageScreenshot =
                loadMovieData.loadMovieImage(movieID, 1, MovieImageTypeImp.SCREENSHOT)
            Log.d("MPViewModel", "SCREENSHOT loaded, size:${imageScreenshot?.imageList?.size}")
            imageScreenshot?.imageList?.forEach { if (imageList.size < 20)  imageList.add(it) }
        }
        _gallery.value = imageList
    }

    private suspend fun loadSeasons(movie: Movie) {
        val movieID = movie.id()
        val seasons = loadSeasons.loadSeasons(movieID)
        if (seasons != null) {
            _seasons.value = seasons
            _episodesNumber.value = seriesCalculation.execute(seasons)
        }
    }

}