package com.example.skillcinema.presentation.fragment.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skillcinema.App
import com.example.skillcinema.databinding.ViewPagerFragmentPersonMovieListBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.decorator.SimpleVerticalItemDecoration
import com.example.skillcinema.presentation.adapter.movieList.MovieListAdapter


class PersonMovieListFragment(
    private val movieList: List<Movie>,
    val name: String,
    val movieNumber: Int,
    val navigate: () -> Unit
) : Fragment() {

    private var _binding: ViewPagerFragmentPersonMovieListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()

    private fun onClickMovie(movie: Movie) {
        mainViewModel.clickOnMovie(movie)
        navigate()
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
        val adapter =
            MovieListAdapter(
                movieList = movieList,
                onClick = { onClickMovie(it) },
                lastElementClick = {})
        binding.filmographyContainer.adapter = adapter
        binding.filmographyContainer.addItemDecoration(
            SimpleVerticalItemDecoration(
                spacing,
                margin
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(
            movieList: List<Movie>, name: String, movieNumber: Int, navigate: () -> Unit
        ) = PersonMovieListFragment(movieList, name, movieNumber, navigate)
    }
}