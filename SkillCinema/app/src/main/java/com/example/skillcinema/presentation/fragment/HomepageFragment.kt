package com.example.skillcinema.presentation.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.DialogLoadingBinding
import com.example.skillcinema.databinding.FragmentHomepageBinding
import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.viewmodel.*
import com.example.skillcinema.presentation.viewmodel.adapter.decorator.HorizontalItemDecoration
import com.example.skillcinema.presentation.viewmodel.adapter.movieList.MovieListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private val homepageViewModel = App.appComponent.homepageViewModel()
    private val mainViewModel = App.appComponent.mainViewModel()
    private val databaseViewModel = App.appComponent.databaseViewModel()
    private var itemDecoration: HorizontalItemDecoration? = null

    private fun onClickMovie(movie: Movie) {
        mainViewModel.clickOnMovie(movie)
        findNavController().navigate(R.id.action_homepageFragment_to_moviePageFragment)
    }

    private fun onClickAllText(movieListType: MovieListType) {
        mainViewModel.setMovieListType(movieListType)
        findNavController().navigate(R.id.action_homepageFragment_to_moviesListPageFragment)
    }

    private fun getAdapter(
        movieList: List<Movie>?,
        movieListType: MovieListType,
        viewedMovies: List<Movie>? = null
    ): MovieListAdapter? {
        return if (movieList != null && movieList.isNotEmpty())
            MovieListAdapter(
                movieList,
                forwardArrow = movieList.size >= 20,
                onClick = { onClickMovie(it) },
                lastElementClick = { onClickAllText(movieListType) },
                viewedMovies = viewedMovies
            )
        else null
    }

    private fun checkIsInformationLoaded() {
        if (
            homepageViewModel.premieres.value == emptyList<Movie>() &&
            homepageViewModel.popularMovies.value == emptyList<Movie>() &&
            homepageViewModel.bestMovies.value == emptyList<Movie>() &&
            homepageViewModel.tvSeries.value == emptyList<Movie>() &&
            homepageViewModel.miniSeries.value == emptyList<Movie>() &&
            homepageViewModel.firstFilteredMovies.value == emptyList<Movie>() &&
            homepageViewModel.secondFilteredMovies.value == emptyList<Movie>()
        ) {
            Log.d("TAG" , " TRUE ${homepageViewModel.premieres.value.isEmpty()}")
            binding.noConnectionLayout.visibility = View.VISIBLE
//            binding.allInformation.visibility = View.GONE
        } else {
            Log.d("TAG" , "FALSE ${homepageViewModel.premieres.value.isEmpty()}")
            binding.noConnectionLayout.visibility = View.GONE
//            binding.allInformation.visibility = View.VISIBLE
        }
    }

    private suspend fun loadInformation(decoration: ItemDecoration? = null) {
        mainViewModel.getApiFilters()
        databaseViewModel.updateCollections()
        loadMovieLists(
            mainViewModel.countryList.value,
            mainViewModel.genreList.value,
            databaseViewModel.viewedMovies.value,
            decoration
        )
        checkIsInformationLoaded()
    }

    private fun refresh() {
        homepageViewModel.refresh()
        viewLifecycleOwner.lifecycleScope.launch {
            loadInformation()
        }
    }

    private suspend fun loadMovieLists(
        countries: List<Country>?,
        genres: List<Genre>?,
        viewedMovies: List<Movie>? = null,
        decoration: ItemDecoration? = null
    ) {
        binding.allInformation.visibility = View.GONE
        homepageViewModel.loadMovieLists(countries, genres)

        val premieres =
            Premieres(homepageViewModel.currentYear.value, homepageViewModel.currentMonth.value)
        binding.premieres.initView(
            getAdapter(homepageViewModel.premieres.value, premieres, viewedMovies),
            itemDecoration = decoration
        ) {
            onClickAllText(premieres)
        }

        binding.popularMovies.initView(
            getAdapter(homepageViewModel.popularMovies.value, PopularMovies, viewedMovies),
            itemDecoration = decoration
        ) { onClickAllText(PopularMovies) }

        binding.bestMovies.initView(
            getAdapter(homepageViewModel.bestMovies.value, BestMovies, viewedMovies),
            itemDecoration = decoration
        ) { onClickAllText(BestMovies) }

        binding.TVSeries.initView(
            getAdapter(homepageViewModel.tvSeries.value, TVSeries, viewedMovies),
            itemDecoration = decoration
        ) { onClickAllText(TVSeries) }

        binding.miniSeries.initView(
            getAdapter(homepageViewModel.miniSeries.value, MiniSeries, viewedMovies),
            itemDecoration = decoration
        ) { onClickAllText(MiniSeries) }

        val firstFilteredMovies = FilteredMovies(
            homepageViewModel.firstCountryFilter.value,
            homepageViewModel.firstGenreFilter.value
        )
        binding.firstRandomList.initView(
            getAdapter(
                homepageViewModel.firstFilteredMovies.value,
                firstFilteredMovies,
                viewedMovies
            ),
            movieListName = homepageViewModel.firstFilteredMovieListName.value,
            itemDecoration = decoration
        ) {
            onClickAllText(firstFilteredMovies)
        }

        val secondFilteredMovies = FilteredMovies(
            homepageViewModel.secondCountryFilter.value,
            homepageViewModel.secondGenreFilter.value
        )
        binding.secondRandomList.initView(
            getAdapter(
                homepageViewModel.secondFilteredMovies.value,
                secondFilteredMovies,
                viewedMovies
            ),
            movieListName = homepageViewModel.secondFilteredMovieListName.value,
            itemDecoration = decoration
        ) {
            onClickAllText(secondFilteredMovies)
        }

        binding.allInformation.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomepageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val margin = (resources.displayMetrics.scaledDensity * START_END_MARGIN).toInt()
        val spacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()

        itemDecoration = HorizontalItemDecoration(
            firstElementMargin = margin,
            horizontalSpacing = spacing
        )

        val dialog = Dialog(requireContext())
        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        homepageViewModel.isMovieListsLoaded.onEach {
            if (it) {
                dialog.dismiss()
                binding.allInformation.visibility = View.VISIBLE
            } else {
                dialog.show()
                binding.allInformation.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch { loadInformation(itemDecoration) }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}