package com.example.skillcinema.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.domain.LoadApiFiltersUseCase
import com.example.skillcinema.domain.SearchUseCase
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.*
import com.example.skillcinema.presentation.adapter.movieList.SearchMoviePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchPageViewModel(
    private val search: SearchUseCase,
    private val loadApiFilters: LoadApiFiltersUseCase
) : ViewModel() {

    private val _type = MutableStateFlow<String>(SEARCH_PARAMETER_TYPE_DEFAULT)
    val type = _type.asStateFlow()
    fun setType(type: String) {
        _type.value = type
        Log.d("ViewModel", "Type: ${_type.value}")
    }

    private val _country = MutableStateFlow<String>(SEARCH_PARAMETER_COUNTRY_DEFAULT)
    val country = _country.asStateFlow()
    fun setCountry(country: String) {
        _country.value = country
        Log.d("ViewModel", "Country: ${_country.value}")
    }

    private val _genre = MutableStateFlow<String>(SEARCH_PARAMETER_GENRE_DEFAULT)
    val genre = _genre.asStateFlow()
    fun setGenre(genre: String) {
        _genre.value = genre
        Log.d("ViewModel", "Genre: ${_genre.value}")
    }

    private val _yearFrom = MutableStateFlow<Int>(SEARCH_PARAMETER_YEAR_FROM_DEFAULT)
    val yearFrom = _yearFrom.asStateFlow()
    fun setYearFrom(yearFrom: Int) {
        _yearFrom.value = yearFrom
        Log.d("ViewModel", "Year from: ${_yearFrom.value}")
    }

    private val _yearTo = MutableStateFlow<Int>(SEARCH_PARAMETER_YEAR_TO_DEFAULT)
    val yearTo = _yearTo.asStateFlow()
    fun setYearTo(yearTo: Int) {
        _yearTo.value = yearTo
        Log.d("ViewModel", "Year to: ${_yearTo.value}")
    }

    private val _ratingFrom = MutableStateFlow<Int>(SEARCH_PARAMETER_RATING_FROM_DEFAULT)
    val ratingFrom = _ratingFrom.asStateFlow()
    fun setRatingFrom(ratingFrom: Int) {
        _ratingFrom.value = ratingFrom
        Log.d("ViewModel", "Rating from: ${_ratingFrom.value}")
    }

    private val _ratingTo = MutableStateFlow<Int>(SEARCH_PARAMETER_RATING_TO_DEFAULT)
    val ratingTo = _ratingTo.asStateFlow()
    fun setRatingTo(ratingTo: Int) {
        _ratingTo.value = ratingTo
        Log.d("ViewModel", "Rating to: ${_ratingTo.value}")
    }

    private val _order = MutableStateFlow<String>(SEARCH_PARAMETER_ORDER_DEFAULT)
    val order = _order.asStateFlow()
    fun setOrder(order: String) {
        _order.value = order
        Log.d("ViewModel", "Order: ${_order.value}")
    }

    private val keyword = MutableStateFlow("")
    fun setKeyword(word: String) {
        keyword.value = word
        Log.d("ViewModel", "Keyword: $keyword")
    }

    private val _isViewed = MutableStateFlow(false)
    val isViewed = _isViewed.asStateFlow()
    fun setIsViewed(isViewed: Boolean) {
        _isViewed.value = isViewed
        Log.d("ViewModel", "Is viewed: ${_isViewed.value}")
    }

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        enablePlaceholders = true
    )

    fun getPagedList(viewedMovies: List<Movie>): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                SearchMoviePagingSource(
                    search = search,
                    countries = countryID,
                    genres = genreID,
                    order = _order.value,
                    type = _type.value,
                    ratingFrom = _ratingFrom.value,
                    ratingTo = _ratingTo.value,
                    yearFrom = _yearFrom.value,
                    yearTo = _yearTo.value,
                    keyword = keyword.value,
                    viewedMovies = viewedMovies,
                    isViewed = _isViewed.value
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private val _settingsFilter = MutableStateFlow<SettingsFilter?>(null)
    val settingsFilter = _settingsFilter.asStateFlow()

    fun setSettingsFilter(filter: SettingsFilter) {
        _settingsFilter.value = filter
    }

    suspend fun checkFilters() {
        val filters = loadApiFilters.execute()
        filters?.countries?.forEach { if (it.name == _country.value) countryID = it.id }
        filters?.genres?.forEach { if (it.name == _genre.value) countryID = it.id }
    }

    private var countryID: Int? = null
    private var genreID: Int? = null

}