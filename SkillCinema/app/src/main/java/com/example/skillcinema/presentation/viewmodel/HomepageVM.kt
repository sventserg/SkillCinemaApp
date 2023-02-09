package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skillcinema.domain.GetMonthName
import com.example.skillcinema.domain.LoadApiFilters
import com.example.skillcinema.domain.LoadMovieList
import com.example.skillcinema.domain.RandomFilter
import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class HomepageVM(
    private val loadApiFilters: LoadApiFilters,
    private val loadMovieList: LoadMovieList,
    private val getMonthName: GetMonthName,
    private val randomFilter: RandomFilter
) : ViewModel() {

    private var isMoviesListsLoaded = false

    /**
     * Function checkDate() gets current year and month,
     * and sets them to the currentYear and currentMonth variables.
     * These variables will be needed when getting premieres list.
     */

    private var _currentYear = MutableStateFlow(VM_PARAMETER_CURRENT_YEAR)
    val currentYear = _currentYear.asStateFlow()
    private var _currentMonth = MutableStateFlow(VM_PARAMETER_CURRENT_MONTH)
    val currentMonth = _currentMonth.asStateFlow()

    private fun checkDate() {
        val date = Calendar.getInstance()
        _currentYear.value = date.get(Calendar.YEAR)
        _currentMonth.value = getMonthName.getMonthName(
            date.get(Calendar.MONTH)
        )
    }

    /**
     *
     */

    private val allMovieLists = mutableMapOf<String, List<Movie>>()

    suspend fun refreshMovieLists(): MutableMap<String, List<Movie>> {
        isMoviesListsLoaded = false
        return getAllMovieLists()
    }

    suspend fun getAllMovieLists(): MutableMap<String, List<Movie>> {
        return if (isMoviesListsLoaded) {
            allMovieLists
        } else {
            loadAllMovieLists()
            saveAllMovieLists()
            isMoviesListsLoaded = true
            allMovieLists
        }
    }

    private suspend fun loadAllMovieLists() {
        getFilteredMoviesLists()
        loadPremieres()
        loadPopularMovie()
        loadBestMovies()
        loadTVSeries()
        loadMiniSeries()
    }

    private fun saveAllMovieLists() {
        val premieres = getPremiereList()
        addMovieList(premieres, VM_PARAMETER_PREMIERES)

        val popularMovies = getPopularMovieList()
        addMovieList(popularMovies, VM_PARAMETER_POPULAR_MOVIES)

        val bestMovies = getBestMovieList()
        addMovieList(bestMovies, VM_PARAMETER_BEST_MOVIES)

        val tvSeries = getTVSeriesList()
        addMovieList(tvSeries, VM_PARAMETER_TV_SERIES)

        val miniSeries = getMiniSeriesList()
        addMovieList(miniSeries, VM_PARAMETER_MINI_SERIES)

        val firstFilteredMovies = getFirstFilteredMoviesList()
        addMovieList(firstFilteredMovies, VM_PARAMETER_FIRST_FILTERED_MOVIES)

        val secondFilteredMovies = getSecondFilteredMoviesList()
        addMovieList(secondFilteredMovies, VM_PARAMETER_SECOND_FILTERED_MOVIES)
    }

    private fun addMovieList(movieList: List<Movie>?, parameter: String) {
        if (movieList != null) {
            allMovieLists[parameter] = movieList
        } else allMovieLists[parameter] = emptyList()
    }

    /**
     * loadApiFilters - gets lists of genres and countries from api,
     * and sets them to genresList and countriesList variables
     */

    private var apiGenresList: List<Genre>? = null
    private var apiCountriesList: List<Country>? = null
    private var firstFilteredMoviesList: List<Movie>? = null
    private var secondFilteredMoviesList: List<Movie>? = null
    private var firstFilteredMoviesListName: String = VM_PARAMETER_TEXT_ERROR
    private var secondFilteredMoviesListName: String = VM_PARAMETER_TEXT_ERROR
    private var _firstCountryFilter = MutableStateFlow<Country?>(null)
    val firstCountryFilter = _firstCountryFilter.asStateFlow()
    private var _secondCountryFilter = MutableStateFlow<Country?>(null)
    val secondCountryFilter = _secondCountryFilter.asStateFlow()
    private var _firstGenreFilter = MutableStateFlow<Genre?>(null)
    val firstGenreFilter = _firstGenreFilter.asStateFlow()
    private var _secondGenreFilter = MutableStateFlow<Genre?>(null)
    val secondGenreFilter = _secondGenreFilter.asStateFlow()

    private fun getFirstFilteredMoviesList(): List<Movie>? = firstFilteredMoviesList
    private fun getSecondFilteredMoviesList(): List<Movie>? = secondFilteredMoviesList

    private suspend fun getFilteredMoviesLists() {
        loadApiFilters()
        getFilteredMoviesListName()
        val firstCountry = _firstCountryFilter.value
        val firstGenre = _firstGenreFilter.value
        val secondCountry = _secondCountryFilter.value
        val secondGenre = _secondGenreFilter.value
        if (firstCountry != null && firstGenre != null) {
            firstFilteredMoviesList = loadFilteredMovies(
                firstCountry, firstGenre
            )
        }
        if (secondCountry != null && secondGenre != null) {
            secondFilteredMoviesList = loadFilteredMovies(
                secondCountry, secondGenre
            )
        }
    }

    private suspend fun loadApiFilters() {
        val filters = loadApiFilters.execute()
        apiGenresList = filters?.genres
        apiCountriesList = filters?.countries
        _firstCountryFilter.value = randomFilter.getRandomCountry(apiCountriesList)
        _secondCountryFilter.value = randomFilter.getRandomCountry(apiCountriesList)
        _firstGenreFilter.value = randomFilter.getRandomGenre(apiGenresList)
        _secondGenreFilter.value = randomFilter.getRandomGenre(apiGenresList)
    }

    private fun getFilteredMoviesListName() {
        val firstCountry = _firstCountryFilter.value
        val firstGenre = _firstGenreFilter.value
        val secondCountry = _secondCountryFilter.value
        val secondGenre = _secondGenreFilter.value
        if (firstCountry != null && firstGenre != null) {
            firstFilteredMoviesListName = firstCountry.name + " " + firstGenre.name
        } else {
            firstFilteredMoviesListName = VM_PARAMETER_TEXT_ERROR
        }
        if (secondCountry != null && secondGenre != null) {
            secondFilteredMoviesListName = secondCountry.name + " " + secondGenre.name
        }
    }

    private suspend fun loadFilteredMovies(
        country: Country,
        genre: Genre
    ): List<Movie> {
        val movieList = loadMovieList.loadFilteredMovies(
            country = country,
            genre = genre,
            page = 1
        )
        return movieList?.movieList ?: emptyList()
    }

    /**
     * premiereList - premiere list from api
     * loadPremieres() - loads premiere list of current month and year
     * getPremieresList() - gets premieres list
     */

    private var premiereList: List<Movie>? = null

    private fun getPremiereList(): List<Movie>? {
        return premiereList
    }

    private suspend fun loadPremieres() {
        checkDate()
        val movieList = loadMovieList.loadPremieres(_currentYear.value, _currentMonth.value)
        if (movieList != null) {
            premiereList = movieList.movieList
        }
    }

    /**
     * popularMovieList - popular movie list from api
     * loadPopularMovie() - loads list of popular movies
     * getPopularMovieList() - gets list of popular movies
     */

    private var popularMovieList: List<Movie>? = null

    private fun getPopularMovieList(): List<Movie>? {
        return popularMovieList
    }

    private suspend fun loadPopularMovie() {
        val movieList = loadMovieList.loadPopularMovies(1)
        if (movieList != null) {
            popularMovieList = movieList.movieList
        }
    }

    /**
     * bestMoviesList - best movies list
     * loadBestMovies() - gets list of best movies from api
     * getBestMoviesList() - gets best movies list
     */

    private var bestMovieList: List<Movie>? = null

    private fun getBestMovieList(): List<Movie>? {
        return bestMovieList
    }

    private suspend fun loadBestMovies() {
        val movieList = loadMovieList.loadBestMovies(1)
        if (movieList != null) {
            bestMovieList = movieList.movieList
        }
    }

    /**
     * tvSeriesList - list of TV series
     * loadTVSeries() - loads list of TV series from api
     * getTVSeriesList() - gets TV series list
     */

    private var tvSeriesList: List<Movie>? = null

    private fun getTVSeriesList(): List<Movie>? {
        return tvSeriesList
    }

    private suspend fun loadTVSeries() {
        val movieList = loadMovieList.loadTVSeries(1)
        if (movieList != null) {
            tvSeriesList = movieList.movieList
        }
    }

    /**
     * miniSeriesList - list of mini series
     * loadMimiSeries() - loads list of mini series from api
     * getMiniSeriesList() - gets list of mini series
     */

    private var miniSeriesList: List<Movie>? = null

    private fun getMiniSeriesList(): List<Movie>? {
        return miniSeriesList
    }

    private suspend fun loadMiniSeries() {
        val movieList = loadMovieList.loadMiniSeries(1)
        if (movieList != null) {
            miniSeriesList = movieList.movieList
        }
    }
}