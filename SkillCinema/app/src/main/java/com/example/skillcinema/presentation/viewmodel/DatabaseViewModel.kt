package com.example.skillcinema.presentation.viewmodel

import com.example.skillcinema.data.database.*
import com.example.skillcinema.domain.DatabaseUseCase
import com.example.skillcinema.domain.GetAppPreferencesUseCase
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.database.DBMovie
import com.example.skillcinema.entity.database.DBUserMovieList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DatabaseViewModel(
    private val database: DatabaseUseCase,
    private val getAppPreferences: GetAppPreferencesUseCase
) {

    //User collections states
    private val _collections = MutableStateFlow<List<DBUserMovieList>>(emptyList())
    val collections = _collections.asStateFlow()
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())

    private val _wantToWatchMovies = MutableStateFlow<List<Movie>>(emptyList())

    private val _viewedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val viewedMovies = _viewedMovies.asStateFlow()
    private val _interestedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val interestedMovies = _interestedMovies.asStateFlow()

    //Is movie in collection states
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()
    private val _isWantToWatch = MutableStateFlow(false)
    val isWantToWatch = _isWantToWatch.asStateFlow()
    private val _isViewed = MutableStateFlow(false)
    val isViewed = _isViewed.asStateFlow()

    //Check is movie in collections
    fun isMovieInCollection(movieID: Int) {
        var boolean = false

        _favoriteMovies.value.forEach {
            if (it.id() == movieID) boolean = true
        }
        _isFavorite.value = boolean

        boolean = false
        _wantToWatchMovies.value.forEach {
            if (it.id() == movieID) boolean = true
        }
        _isWantToWatch.value = boolean

        boolean = false
        _viewedMovies.value.forEach {
            if (it.id() == movieID) boolean = true
        }
        _isViewed.value = boolean
    }

    //Update database
    suspend fun updateCollections() {
        val savedMovieLists = getSavedMovieLists()
        val favoriteMovieList = mutableListOf<Movie>()
        val wantToWatchMovieList = mutableListOf<Movie>()
        val interestedMovieList = mutableListOf<Movie>()
        val viewedMovieList = mutableListOf<Movie>()
        val collections = mutableListOf<DBUserMovieList>()
        savedMovieLists.forEach { DBUserMovieList ->
            when (DBUserMovieList.savedMovieList.listName) {
                FAVORITES_MOVIES_NAME -> {
                    collections.add(DBUserMovieList)
                    DBUserMovieList.movies.forEach {
                        favoriteMovieList.add(MovieFromDB(it))
                    }
                }
                WANT_TO_WATCH_MOVIES_NAME -> {
                    collections.add(DBUserMovieList)
                    DBUserMovieList.movies.forEach {
                        wantToWatchMovieList.add(MovieFromDB(it))
                    }
                }
                INTERESTED_MOVIES_NAME -> {}
                VIEWED_MOVIES_NAME -> {}
                else -> collections.add(DBUserMovieList)
            }
        }
        val viewedMovies = getSavedMovieList(VIEWED_MOVIES_NAME).reversed()
        val interestedMovies = getSavedMovieList(INTERESTED_MOVIES_NAME).reversed()

        viewedMovies.forEach { viewedMovieList.add(MovieFromDB(it)) }
        interestedMovies.forEach { interestedMovieList.add(MovieFromDB(it)) }
        _favoriteMovies.value = favoriteMovieList
        _wantToWatchMovies.value = wantToWatchMovieList
        _interestedMovies.value = interestedMovieList
        _viewedMovies.value = viewedMovieList
        _collections.value = collections
    }

    //Save movie to collection
    fun saveMovieToCollection(movie: Movie, listName: String, time: Long) {
        val job = Job(null)
        val scope = CoroutineScope(job)
        scope.launch {
            database.insertMovie(movie)
            database.insertMovieToMovieList(movie.id(), listName, time)
            updateCollections()
            isMovieInCollection(movie.id())
            scope.cancel()
        }
    }

    fun saveMovieToFavorite(movie: Movie, time: Long) {
        saveMovieToCollection(movie, FAVORITES_MOVIES_NAME, time)
    }

    fun saveMovieToWantToWatch(movie: Movie, time: Long) {
        saveMovieToCollection(movie, WANT_TO_WATCH_MOVIES_NAME, time)
    }

    fun saveMovieToViewed(movie: Movie, time: Long) {
        saveMovieToCollection(movie, VIEWED_MOVIES_NAME, time)
    }

    fun saveMovieToInterested(movie: Movie, time: Long) {
        saveMovieToCollection(movie, INTERESTED_MOVIES_NAME, time)
    }

    fun deleteMovieFromCollection(movieID: Int, listName: String) {
        val job = Job(null)
        val scope = CoroutineScope(job)
        scope.launch {
            database.deleteMovieFromMovieList(movieID, listName)
            updateCollections()
            isMovieInCollection(movieID)
            scope.cancel()
        }
    }

    fun deleteMovieFromFavorites(movieID: Int) {
        deleteMovieFromCollection(movieID, FAVORITES_MOVIES_NAME)
    }

    fun deleteMovieFromWantToWatchMovies(movieID: Int) {
        deleteMovieFromCollection(movieID, WANT_TO_WATCH_MOVIES_NAME)
    }

    fun deleteMovieFromViewedMovies(movieID: Int) {
        deleteMovieFromCollection(movieID, VIEWED_MOVIES_NAME)
    }

    fun deleteMovieFromInterestedMovies(movieID: Int) {
        deleteMovieFromCollection(movieID, INTERESTED_MOVIES_NAME)
    }

    fun clearInterestedCollection() {
        val job = Job(null)
        val scope = CoroutineScope(job)
        scope.launch { database.clearCollection(INTERESTED_MOVIES_NAME) }
        _interestedMovies.value = emptyList()
    }

    fun clearViewedCollection() {
        val job = Job(null)
        val scope = CoroutineScope(job)
        scope.launch { database.clearCollection(VIEWED_MOVIES_NAME) }
        _viewedMovies.value = emptyList()
    }

    suspend fun deleteMovie(id: Int) {
        database.deleteMovie(id)
    }

    private suspend fun getSavedMovieLists(): List<DBUserMovieList> {
        return database.getSavedMovieLists()
    }

    private suspend fun getSavedMovieList(name: String): List<DBMovie> {
        return database.getSavedMovieList(name)
    }

    suspend fun insertMovieList(name: String) {
        val movieLists = getSavedMovieLists()
        movieLists.forEach {
            if (it.savedMovieList.listName == name) {
                return
            }
        }
        val movieList = DBMovieListImp(name)
        database.insertMovieList(movieList)
        updateCollections()
    }

    suspend fun deleteMovieList(name: String) {
        deleteMovieListMovies(name)
        database.deleteMovieList(name)
        updateCollections()
    }

    private suspend fun deleteMovieListMovies(listName: String) {
        database.deleteMovieListMovies(listName)
    }

    fun checkMovieListName(name: String): Boolean {
        var boolean = false
        _collections.value.forEach {
            if (it.savedMovieList.listName.equals(name, true)) boolean = true
        }
        return boolean
    }

    /***
     * Preferences
     */

    fun isOnBoardingNeeded(): Boolean {
        return getAppPreferences.isOnBoardingNeeded()
    }

    fun onBoardingIsOver() {
        getAppPreferences.onBoardingIsOver()
    }
}