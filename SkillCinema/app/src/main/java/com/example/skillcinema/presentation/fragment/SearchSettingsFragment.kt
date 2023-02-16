package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSearchSettingsBinding
import com.example.skillcinema.presentation.*
import com.example.skillcinema.presentation.SettingsFilter
import kotlinx.coroutines.launch

class SearchSettingsFragment : Fragment() {

    private var _binding: FragmentSearchSettingsBinding? = null
    private val binding get() = _binding!!
    private val searchPageVM = App.appComponent.searchPageVM()
    private val mainViewModel = App.appComponent.mainViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pageName.init {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        //Search type filter
        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.type.collect {
                when (it) {
                    SEARCH_PARAMETER_TYPE_DEFAULT -> {
                        binding.allFilter.isSelected = true
                        binding.moviesFilter.isSelected = false
                        binding.seriesFilter.isSelected = false
                    }
                    SEARCH_PARAMETER_TYPE_MOVIES -> {
                        binding.allFilter.isSelected = false
                        binding.moviesFilter.isSelected = true
                        binding.seriesFilter.isSelected = false
                    }
                    SEARCH_PARAMETER_TYPE_SERIES -> {
                        binding.allFilter.isSelected = false
                        binding.moviesFilter.isSelected = false
                        binding.seriesFilter.isSelected = true
                    }
                }
            }
        }

        //All type
        binding.allFilter.setOnClickListener {
            searchPageVM.setType(SEARCH_PARAMETER_TYPE_DEFAULT)
        }

        //Only movies
        binding.moviesFilter.setOnClickListener {
            searchPageVM.setType(SEARCH_PARAMETER_TYPE_MOVIES)
        }

        //Only TV Series
        binding.seriesFilter.setOnClickListener {
            searchPageVM.setType(SEARCH_PARAMETER_TYPE_SERIES)
        }

        //Sorting filter
        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.order.collect {
                when (it) {
                    SEARCH_PARAMETER_ORDER_DEFAULT -> {
                        binding.dateFilter.isSelected = false
                        binding.popularityFilter.isSelected = false
                        binding.ratingFilter.isSelected = true
                    }
                    SEARCH_PARAMETER_ORDER_YEAR -> {
                        binding.dateFilter.isSelected = true
                        binding.popularityFilter.isSelected = false
                        binding.ratingFilter.isSelected = false
                    }
                    SEARCH_PARAMETER_ORDER_NUM_VOTE -> {
                        binding.dateFilter.isSelected = false
                        binding.popularityFilter.isSelected = true
                        binding.ratingFilter.isSelected = false
                    }
                }
            }
        }

        //Sort by date
        binding.dateFilter.setOnClickListener {
            searchPageVM.setOrder(SEARCH_PARAMETER_ORDER_YEAR)
        }

        //Sort by popularity
        binding.popularityFilter.setOnClickListener {
            searchPageVM.setOrder(SEARCH_PARAMETER_ORDER_NUM_VOTE)
        }

        //Sort by rating
        binding.ratingFilter.setOnClickListener {
            searchPageVM.setOrder(SEARCH_PARAMETER_ORDER_DEFAULT)
        }

        //Rating range
        binding.ratingSeekBar.values = mutableListOf(
            searchPageVM.ratingFrom.value.toFloat(),
            searchPageVM.ratingTo.value.toFloat()
        )
        getRatingRangeText()

        //Country and genre filters
        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.country.collect {
                binding.country.text = it
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.genre.collect {
                binding.genre.text = it
            }
        }

        //Rating change listener
        binding.ratingSeekBar.addOnChangeListener { _, _, _ ->
            searchPageVM.setRatingFrom(binding.ratingSeekBar.values.first().toInt())
            searchPageVM.setRatingTo(binding.ratingSeekBar.values.last().toInt())
            getRatingRangeText()
        }

        //Get api filters
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getApiFilters()
        }

        //Navigate to country filter
        binding.countryLayout.setOnClickListener {
            val countryList = mainViewModel.countryList.value
            if (countryList != null) {
                searchPageVM.setSettingsFilter(
                    SettingsFilter.COUNTRY(countryList)
                )
                findNavController().navigate(R.id.action_searchSettingsFragment_to_searchFilterFragment)
            }
        }

        //Navigate to genre filter
        binding.genreLayout.setOnClickListener {
            val genreList = mainViewModel.genreList.value
            if (genreList != null) {
                searchPageVM.setSettingsFilter(SettingsFilter.GENRE(genreList))
                findNavController().navigate(R.id.action_searchSettingsFragment_to_searchFilterFragment)
            }
        }

        //Navigate to year selector
        binding.yearLayout.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingsFragment_to_setTimePeriodFragment)
        }

        //Year filter
        getYearRangeText()

        //Viewed filter
        binding.viewedFilter.setOnClickListener {
            if (searchPageVM.isViewed.value) searchPageVM.setIsViewed(false)
            else searchPageVM.setIsViewed(true)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.isViewed.collect {
                if (it) {
                    binding.isViewedIcon.setImageResource(R.drawable.search_viewed_icon)
                    binding.isViewedText.text = getString(R.string.viewed)
                } else {
                    binding.isViewedIcon.setImageResource(R.drawable.search_not_viewed_icon)
                    binding.isViewedText.text = getString(R.string.not_viewed)
                }
            }
        }
        binding.defaultSettings.setOnClickListener {
            searchPageVM.setType(SEARCH_PARAMETER_TYPE_DEFAULT)
            searchPageVM.setOrder(SEARCH_PARAMETER_ORDER_DEFAULT)
            searchPageVM.setCountry(SEARCH_PARAMETER_COUNTRY_DEFAULT)
            searchPageVM.setGenre(SEARCH_PARAMETER_GENRE_DEFAULT)
            searchPageVM.setYearFrom(SEARCH_PARAMETER_YEAR_FROM_DEFAULT)
            searchPageVM.setYearTo(SEARCH_PARAMETER_YEAR_TO_DEFAULT)
            searchPageVM.setRatingFrom(SEARCH_PARAMETER_RATING_FROM_DEFAULT)
            searchPageVM.setRatingTo(SEARCH_PARAMETER_RATING_TO_DEFAULT)
            searchPageVM.setIsViewed(false)

            getYearRangeText()
            binding.ratingSeekBar.values = mutableListOf(
                searchPageVM.ratingFrom.value.toFloat(),
                searchPageVM.ratingTo.value.toFloat()
            )
            getRatingRangeText()
        }
    }

    private fun getRatingRangeText() {
        val ratingFrom = binding.ratingSeekBar.values.first().toInt()
        val ratingTo = binding.ratingSeekBar.values.last().toInt()
        if (ratingFrom == 0 && ratingTo == 10) {
            binding.ratingText.text = getString(R.string.does_not_matter)
        } else {
            binding.ratingText.text = getString(R.string.rating_range, ratingFrom, ratingTo)
        }
    }

    private fun getYearRangeText() {
        val yearFrom = searchPageVM.yearFrom.value
        val yearTo = searchPageVM.yearTo.value
        if (yearFrom == SEARCH_PARAMETER_YEAR_FROM_DEFAULT && yearTo == SEARCH_PARAMETER_YEAR_TO_DEFAULT) {
            binding.yearRange.text = getString(R.string.does_not_matter)
        } else {
            binding.yearRange.text =
                getString(
                    R.string.year_range,
                    searchPageVM.yearFrom.value,
                    searchPageVM.yearTo.value
                )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}