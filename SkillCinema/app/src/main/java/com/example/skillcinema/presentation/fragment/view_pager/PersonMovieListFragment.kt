package com.example.skillcinema.presentation.fragment.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.skillcinema.App
import com.example.skillcinema.databinding.ViewPagerFragmentPersonMovieListBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.adapter.movieList.PagingMovieListAdapter
import com.example.skillcinema.presentation.decorator.SimpleVerticalItemDecoration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class PersonMovieListFragment(
//    private val movieList: List<Movie>,
    private val pagingMovies: Flow<PagingData<Movie>>,
    val viewedMovies: List<Movie>,
    val name: String,
    val movieNumber: Int,
    val navigate: () -> Unit
) : Fragment() {

    private var _binding: ViewPagerFragmentPersonMovieListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()

    private fun onClickMovie(movie: Movie?) {
        if (movie != null) {
            mainViewModel.clickOnMovie(movie)
            navigate()
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
//        binding.filmographyContainer.adapter = MovieListAdapter(
//            movieList = movieList,
//            onClick = { onClickMovie(it) },
//            lastElementClick = {})
        binding.filmographyContainer.addItemDecoration(
            SimpleVerticalItemDecoration(
                spacing,
                margin
            )
        )
        val adapter = PagingMovieListAdapter(viewedMovies) { onClickMovie(it) }
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
            pagingMovies: Flow<PagingData<Movie>>,
            viewedMovies: List<Movie>,
            name: String,
            movieNumber: Int,
            navigate: () -> Unit
        ) = PersonMovieListFragment(pagingMovies, viewedMovies, name, movieNumber, navigate)
    }
}