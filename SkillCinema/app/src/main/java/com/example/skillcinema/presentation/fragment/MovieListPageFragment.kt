package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentMovieListPageBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.MOVIE_LIST_PAGE_SPACING
import com.example.skillcinema.presentation.viewmodel.adapter.decorator.VerticalItemDecoration
import com.example.skillcinema.presentation.viewmodel.adapter.decorator.VerticalItemDecorationTwoColumn
import com.example.skillcinema.presentation.viewmodel.adapter.loadState.MovieLoadStateAdapter
import com.example.skillcinema.presentation.viewmodel.adapter.movieList.PagingMovieListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MovieListPageFragment : Fragment() {

    private var _binding: FragmentMovieListPageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val movieListPageVM = App.appComponent.movieListPageVM()

    private fun onClickMovie(movie: Movie?) {
        if (movie != null) {
            mainViewModel.clickOnMovie(movie)
            findNavController().navigate(R.id.action_moviesListPageFragment_to_moviePageFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val horizontalSpacing =
            (resources.displayMetrics.scaledDensity * MOVIE_LIST_PAGE_SPACING).toInt()
        val verticalSpacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()

        val adapter = PagingMovieListAdapter { onClickMovie(it) }
        binding.moviesList.adapter = adapter.withLoadStateFooter(MovieLoadStateAdapter())
        binding.moviesList.addItemDecoration(
            VerticalItemDecorationTwoColumn(
                verticalSpacing,
                horizontalSpacing
            )
        )

        val movieListType = mainViewModel.movieListType.value
        if (movieListType != null) {
            movieListPageVM.setMovieListType(movieListType, requireContext())
            movieListPageVM.getPagedList(movieListType).onEach {
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            binding.pageName.init(
                pageName = movieListPageVM.movieListName.value,
                onBackButtonClick = {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            )
        }

        binding.pageName.init(
            pageName = movieListPageVM.movieListName.value,
            onBackButtonClick = {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        )

        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
        adapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading

            if (it.refresh != LoadState.Loading) {
                if (adapter.snapshot().isEmpty()) {
                    binding.noConnectionLayout.visibility = View.VISIBLE
                } else
                    binding.noConnectionLayout.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
