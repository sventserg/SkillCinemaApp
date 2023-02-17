package com.example.skillcinema.presentation.fragment.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar.Tab
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.data.GetPersonProfessionName
import com.example.skillcinema.databinding.ViewPagerFragmentPersonMovieListBinding
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.adapter.movieList.LoadMoviePagingSource
import com.example.skillcinema.presentation.adapter.movieList.PagingMovieListAdapter
import com.example.skillcinema.presentation.decorator.SimpleVerticalItemDecoration
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class PersonMovieListFragment(
    private val movieList: List<PersonMovie>,
    val professionKey: String
) : Fragment() {

    val movieNumber = movieList.size
    private var _binding: ViewPagerFragmentPersonMovieListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val filmographyVM = App.appComponent.filmographyVM()
    private val databaseViewModel = App.appComponent.databaseViewModel()

    private fun onClickMovie(movie: Movie?) {
        if (movie != null) {
            mainViewModel.clickOnMovie(movie)
            findNavController().navigate(R.id.action_filmographyFragment_to_moviePageFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewPagerFragmentPersonMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()
        val margin = (resources.displayMetrics.scaledDensity * START_END_MARGIN).toInt()

        binding.filmographyContainer.addItemDecoration(
            SimpleVerticalItemDecoration(
                spacing,
                margin
            )
        )

        val pagingMovies = filmographyVM.getPagingMovies(movieList)

        val adapter =
            PagingMovieListAdapter(databaseViewModel.viewedMovies.value) { onClickMovie(it) }
        binding.filmographyContainer.adapter = adapter
        pagingMovies.onEach { adapter.submitData(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        binding.filmographyContainer.adapter = null
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(
            movieList: List<PersonMovie>,
            professionKey: String
        ) = PersonMovieListFragment(movieList, professionKey)
    }
}