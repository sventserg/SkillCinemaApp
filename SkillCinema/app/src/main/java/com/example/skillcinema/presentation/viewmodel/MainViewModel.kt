package com.example.skillcinema.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.skillcinema.R
import com.example.skillcinema.domain.LoadApiFilters
import com.example.skillcinema.domain.LoadMovieData
import com.example.skillcinema.entity.*
import com.example.skillcinema.presentation.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val loadMovieData: LoadMovieData,
    private val loadApiFilters: LoadApiFilters
) : ViewModel() {

    suspend fun loadMovieData(id: Int): Movie? {
        return loadMovieData.loadMovie(id)
    }

    private val _movieListType = MutableStateFlow<MovieListType?>(null)
    val movieListType = _movieListType.asStateFlow()

    fun setMovieListType(type: MovieListType) {
        when (type) {
            is Premieres -> _movieListType.value = Premieres(
                year = type.year,
                month = type.month
            )
            BestMovies -> _movieListType.value = BestMovies
            is FilteredMovies -> _movieListType.value = FilteredMovies(
                country = type.country,
                genre = type.genre
            )
            MiniSeries -> _movieListType.value = MiniSeries
            PopularMovies -> _movieListType.value = PopularMovies
            TVSeries -> _movieListType.value = TVSeries
            is MovieCollectionType -> _movieListType.value = MovieCollectionType(
                movieList = type.movieList,
                name = type.name
            )
        }
    }

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    fun clickOnMovie(movie: Movie) {
        _selectedMovie.value = movie
        Log.d("ViewModel", "set movie, ID: ${movie.id()}")
    }

    private val _selectedStaffPerson = MutableStateFlow<Staff?>(null)
    val selectedStaffPerson = _selectedStaffPerson.asStateFlow()

    fun clickOnPerson(staff: Staff) {
        _selectedStaffPerson.value = staff
    }

    private val _staff = MutableStateFlow<List<Staff>?>(null)
    val staff = _staff.asStateFlow()

    private val _staffText = MutableStateFlow<String?>(null)
    val staffText = _staffText.asStateFlow()

    fun setStaff(staff: List<Staff>, text: String) {
        _staff.value = staff
        _staffText.value = text
    }

    private val _person = MutableStateFlow<Person?>(null)
    val person = _person.asStateFlow()

    fun setPerson(person: Person) {
        _person.value = person
    }

    /***
     * Api filters
     */

    private val _countryList = MutableStateFlow<List<Country>?>(null)
    val countryList = _countryList.asStateFlow()

    private val _genreList = MutableStateFlow<List<Genre>?>(null)
    val genreList = _genreList.asStateFlow()

    suspend fun getApiFilters() {
        val filters = loadApiFilters.execute()
        _countryList.value = filters?.countries
        _genreList.value = filters?.genres
    }

    private val _countryID = MutableStateFlow<Int?>(null)
    val countryID = _countryID.asStateFlow()
    private val _genreID = MutableStateFlow<Int?>(null)
    val genreID = _genreID.asStateFlow()

    fun setFilterID(countryName: String, genreName: String) {
        _countryList.value?.forEach { if (it.name == countryName) _countryID.value = it.id }
        _genreList.value?.forEach { if (it.name == genreName) _genreID.value = it.id }
    }


    private val _seasons = MutableStateFlow<Seasons?>(null)
    val seasons = _seasons.asStateFlow()
    private val _episodesNumber = MutableStateFlow<String?>(null)
    val episodesNumber = _episodesNumber.asStateFlow()

    fun setSeasons(seasons: Seasons) {
        _seasons.value = seasons
    }

    fun setEpisodesNumber(episodesNumber: String) {
        _episodesNumber.value = episodesNumber
    }

    private val _galleryTypeNumbers = MutableStateFlow<Map<String, Int>>(emptyMap())
    val galleryTypeNumbers = _galleryTypeNumbers.asStateFlow()

    fun setGalleryTypeNumbers(galleryTypeNumbers: Map<String, Int>) {
        _galleryTypeNumbers.value = galleryTypeNumbers
    }
}