package com.example.skillcinema.presentation.fragment.VPFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentPersonMovieListBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.viewmodel.adapter.decorator.SimpleVerticalItemDecoration
import com.example.skillcinema.presentation.viewmodel.adapter.movieList.MovieListAdapter


class PersonMovieListFragment(
    private val movieList: List<Movie>,
    val name: String,
    val movieNumber: Int
) : Fragment() {

    private var _binding: FragmentPersonMovieListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()

    private fun onClickMovie(movie: Movie) {
        mainViewModel.clickOnMovie(movie)
        findNavController().navigate(R.id.action_filmographyFragment_to_moviePageFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonMovieListBinding.inflate(inflater)
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
            movieList: List<Movie>, name: String, movieNumber: Int
        ) = PersonMovieListFragment(movieList, name, movieNumber)
    }
}