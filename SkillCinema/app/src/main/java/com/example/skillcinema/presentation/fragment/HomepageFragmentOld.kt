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
import com.example.skillcinema.databinding.FragmentHomepageOldBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.*
import com.example.skillcinema.presentation.viewmodel.*
import com.example.skillcinema.presentation.viewmodel.adapter.movieList.MovieListAdapter
import kotlinx.coroutines.launch

class HomepageFragmentOld : Fragment() {

    private fun onClickMovie(movie: Movie) {
        mainViewModel.clickOnMovie(movie)
        findNavController().navigate(R.id.action_homepageFragment_to_moviePageFragment)
    }

    private var _binding: FragmentHomepageOldBinding? = null
    private val binding get() = _binding!!
    private val homepageVM = App.appComponent.homepageVM()
    private val mainViewModel = App.appComponent.mainViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomepageOldBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val movieLists = homepageVM.getAllMovieLists()
            loadMovieLists(movieLists)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val movieLists = homepageVM.refreshMovieLists()
                loadMovieLists(movieLists)
                binding.swipeRefresh.isRefreshing = false
            }
        }

        binding.allPremieres.setOnClickListener {
            mainViewModel.setMovieListType(
                Premieres(
                    homepageVM.currentYear.value,
                    homepageVM.currentMonth.value
                )
            )
            findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
        }
        binding.allPopularMovies.setOnClickListener {
            mainViewModel.setMovieListType(PopularMovies)
            findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
        }
        binding.allBestMovies.setOnClickListener {
            mainViewModel.setMovieListType(BestMovies)
            findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
        }
        binding.allTVSeries.setOnClickListener {
            mainViewModel.setMovieListType(TVSeries)
            findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
        }
        binding.allMiniSeries.setOnClickListener {
            mainViewModel.setMovieListType(MiniSeries)
            findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
        }
        binding.allFirstFilteredMovies.setOnClickListener {
            val country = homepageVM.firstCountryFilter.value
            val genre = homepageVM.firstGenreFilter.value
            if (country != null && genre != null) {
                mainViewModel.setMovieListType(
                    FilteredMovies(
                        country, genre
                    )
                )
                findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
            }
        }
        binding.allSecondFilteredMovies.setOnClickListener {
            val country = homepageVM.secondCountryFilter.value
            val genre = homepageVM.secondGenreFilter.value
            if (country != null && genre != null) {
                mainViewModel.setMovieListType(
                    FilteredMovies(
                        country, genre
                    )
                )
                findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
            }
        }
    }

//    private fun getAdapter(
//        movieList: List<Movie>?
//    ): MovieListAdapter {
//        return if (movieList != null)
//            MovieListAdapter(movieList) { onClickMovie(it) }
//        else MovieListAdapter(emptyList()) { onClickMovie(it) }
//    }

    private fun loadMovieLists(
        movieLists: MutableMap<String, List<Movie>>
    ) {
        val premieres = movieLists[VM_PARAMETER_PREMIERES]
        val popularMovies = movieLists[VM_PARAMETER_POPULAR_MOVIES]
        val bestMovies = movieLists[VM_PARAMETER_BEST_MOVIES]
        val tvSeries = movieLists[VM_PARAMETER_TV_SERIES]
        val miniSeries = movieLists[VM_PARAMETER_MINI_SERIES]
        val firstFilteredMovies = movieLists[VM_PARAMETER_FIRST_FILTERED_MOVIES]
        val secondFilteredMovies = movieLists[VM_PARAMETER_SECOND_FILTERED_MOVIES]

//        binding.premieres.adapter = getAdapter(premieres)
//        binding.popularMovies.adapter = getAdapter(popularMovies)
//        binding.bestMovies.adapter = getAdapter(bestMovies)
//        binding.TVSeries.adapter = getAdapter(tvSeries)
//        binding.miniSeries.adapter = getAdapter(miniSeries)
//        binding.firstFilteredMovies.adapter = getAdapter(firstFilteredMovies)
//        binding.secondFilteredMovies.adapter = getAdapter(secondFilteredMovies)
//        binding.firstFilteredMoviesText.text = homepageVM.getFirstFilteredMoviesListName()
//        binding.secondFilteredMoviesText.text = homepageVM.getSecondFilteredMoviesListName()
//        binding.customPremieres.getMovieList(getAdapter(premieres), "Премьеры", "Все")
//        binding.customBest.getMovieList(getAdapter(bestMovies), "Топ-250", "Все")
    }
}