package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skillcinema.data.NO_MOVIE_ID
import com.example.skillcinema.domain.GetMonthName
import com.example.skillcinema.domain.LoadMovieData
import com.example.skillcinema.domain.LoadMovieList
import com.example.skillcinema.domain.RandomFilter
import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.VM_PARAMETER_CURRENT_MONTH
import com.example.skillcinema.presentation.VM_PARAMETER_CURRENT_YEAR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class HomepageViewModel(
    private val loadMovieList: LoadMovieList,
    private val getMonthName: GetMonthName,
    private val randomFilter: RandomFilter,
    private val loadMovieData: LoadMovieData
) : ViewModel() {

    private val _isMovieListsLoaded = MutableStateFlow(false)
    val isMovieListsLoaded = _isMovieListsLoaded.asStateFlow()

    /**
     * Function checkDate() gets current year and month,
     * and sets them to the currentYear and currentMonth variables.
     * These variables will be needed when getting premieres list.
     */

    private val _currentYear = MutableStateFlow(VM_PARAMETER_CURRENT_YEAR)
    val currentYear = _currentYear.asStateFlow()
    private val _currentMonth = MutableStateFlow(VM_PARAMETER_CURRENT_MONTH)
    val currentMonth = _currentMonth.asStateFlow()

    private fun checkDate() {
        val date = Calendar.getInstance()
        _currentYear.value = date.get(Calendar.YEAR)
        _currentMonth.value = getMonthName.getMonthName(
            date.get(Calendar.MONTH)
        )
    }

    private val _premieres = MutableStateFlow<List<Movie>>(emptyList())
    val premieres = _premieres.asStateFlow()
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()
    private val _bestMovies = MutableStateFlow<List<Movie>>(emptyList())
    val bestMovies = _bestMovies.asStateFlow()
    private val _tvSeries = MutableStateFlow<List<Movie>>(emptyList())
    val tvSeries = _tvSeries.asStateFlow()
    private val _miniSeries = MutableStateFlow<List<Movie>>(emptyList())
    val miniSeries = _miniSeries.asStateFlow()

    private val _firstFilteredMovies = MutableStateFlow<List<Movie>>(emptyList())
    val firstFilteredMovies = _firstFilteredMovies.asStateFlow()
    private val _secondFilteredMovies = MutableStateFlow<List<Movie>>(emptyList())
    val secondFilteredMovies = _secondFilteredMovies.asStateFlow()
    private val _firstFilteredMovieListName = MutableStateFlow("Без названия")
    val firstFilteredMovieListName = _firstFilteredMovieListName.asStateFlow()
    private val _secondFilteredMovieListName = MutableStateFlow("Без названия")
    val secondFilteredMovieListName = _secondFilteredMovieListName.asStateFlow()

    private val _firstCountryFilter = MutableStateFlow<Country?>(null)
    val firstCountryFilter = _firstCountryFilter.asStateFlow()
    private val _secondCountryFilter = MutableStateFlow<Country?>(null)
    val secondCountryFilter = _secondCountryFilter.asStateFlow()
    private val _firstGenreFilter = MutableStateFlow<Genre?>(null)
    val firstGenreFilter = _firstGenreFilter.asStateFlow()
    private val _secondGenreFilter = MutableStateFlow<Genre?>(null)
    val secondGenreFilter = _secondGenreFilter.asStateFlow()

    private suspend fun loadPremieres() {
        val movieList =
            loadMovieList.loadPremieres(_currentYear.value, _currentMonth.value)?.movieList
        if (movieList != null) _premieres.value = loadMovies(movieList)
    }

    private suspend fun loadPopularMovies() {
        val movieList = loadMovieList.loadPopularMovies(1)?.movieList
        if (movieList != null) _popularMovies.value = movieList
    }

    private suspend fun loadBestMovies() {
        val movieList = loadMovieList.loadBestMovies(1)?.movieList
        if (movieList != null) _bestMovies.value = movieList
    }

    private suspend fun loadTVSeries() {
        val movieList = loadMovieList.loadTVSeries(1)?.movieList
        if (movieList != null) _tvSeries.value = movieList
    }

    private suspend fun loadMiniSeries() {
        val movieList = loadMovieList.loadMiniSeries(1)?.movieList
        if (movieList != null) _miniSeries.value = movieList
    }

    private suspend fun loadFilteredMovies(
        countries: List<Country>,
        genres: List<Genre>
    ) {
        while (_firstFilteredMovies.value.isEmpty() || _secondFilteredMovies.value.isEmpty()) {
            loadFilters(countries, genres)
            val firstCountry = _firstCountryFilter.value
            val secondCountry = _secondCountryFilter.value
            val firstGenre = _firstGenreFilter.value
            val secondGenre = _secondGenreFilter.value
            if (firstCountry != null && firstGenre != null) {
                _firstFilteredMovieListName.value =
                    "${firstGenre.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}  ${firstCountry.name}"
                val firstResult =
                    loadMovieList.loadFilteredMovies(firstCountry, firstGenre, 1)?.movieList
                if (firstResult != null) _firstFilteredMovies.value = firstResult
            }
            if (secondCountry != null && secondGenre != null) {
                _secondFilteredMovieListName.value =
                    "${secondGenre.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}  ${secondCountry.name}"
                val secondResult =
                    loadMovieList.loadFilteredMovies(secondCountry, secondGenre, 1)?.movieList
                if (secondResult != null) _secondFilteredMovies.value = secondResult
            }
        }
    }

    private fun loadFilters(
        countries: List<Country>,
        genres: List<Genre>
    ) {
        _firstCountryFilter.value = randomFilter.getRandomCountry(countries)
        _secondCountryFilter.value = randomFilter.getRandomCountry(countries)
        while (_firstCountryFilter.value == _secondCountryFilter.value) {
            _secondCountryFilter.value = randomFilter.getRandomCountry(countries)
        }
        _firstGenreFilter.value = randomFilter.getRandomGenre(genres)
        _secondGenreFilter.value = randomFilter.getRandomGenre(genres)
        while (_firstGenreFilter.value == _secondGenreFilter.value) {
            _secondGenreFilter.value = randomFilter.getRandomGenre(genres)
        }
    }

    suspend fun loadMovieLists(
        countries: List<Country>?,
        genres: List<Genre>?
    ) {
        if (!_isMovieListsLoaded.value) {
            checkDate()
            loadPremieres()
            loadPopularMovies()
            loadBestMovies()
            loadTVSeries()
            loadMiniSeries()
            if (countries != null && genres != null) {
                loadFilteredMovies(countries, genres)
            }
            _isMovieListsLoaded.value = true
        }
    }

    fun refresh() {
        checkDate()
        _isMovieListsLoaded.value = false
        _premieres.value = emptyList()
        _popularMovies.value = emptyList()
        _bestMovies.value = emptyList()
        _tvSeries.value = emptyList()
        _miniSeries.value = emptyList()
        _firstFilteredMovies.value = emptyList()
        _secondFilteredMovies.value = emptyList()
    }

    private suspend fun loadMovies(movieList: List<Movie>): List<Movie> {
        val newMovieList = mutableListOf<Movie>()
        var i = 0
        while (i < 20 && i < movieList.size - 1) {
            val kinopoiskID = movieList[i].id()
            if (kinopoiskID != NO_MOVIE_ID) {
                val movie = loadMovieData.loadMovie(kinopoiskID)
                if (movie != null) {
                    newMovieList.add(movie)
                }
            }
            i++
        }
        return newMovieList
    }
}