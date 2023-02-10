package com.example.skillcinema.presentation.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.data.database.INTERESTED_MOVIES_NAME
import com.example.skillcinema.data.database.MovieFromDB
import com.example.skillcinema.data.database.VIEWED_MOVIES_NAME
import com.example.skillcinema.databinding.DialogErrorBinding
import com.example.skillcinema.databinding.DialogNewCollectionBinding
import com.example.skillcinema.databinding.FragmentUserProfilePageBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.database.DBUserMovieList
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.MovieCollectionType
import com.example.skillcinema.presentation.adapter.collection.CollectionAdapter
import com.example.skillcinema.presentation.decorator.HorizontalItemDecoration
import com.example.skillcinema.presentation.adapter.movieList.MovieListAdapter
import com.example.skillcinema.presentation.decorator.VerticalItemDecoration
import com.example.skillcinema.presentation.decorator.VerticalItemDecorationTwoColumn
import kotlinx.coroutines.launch
import kotlin.math.min


class UserProfilePageFragment : Fragment() {

    private var _binding: FragmentUserProfilePageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val databaseViewModel = App.appComponent.databaseViewModel()

    private fun onClickMovie(movie: Movie?) {
        if (movie != null) {
            mainViewModel.clickOnMovie(movie)
            findNavController().navigate(R.id.action_profilePageFragment_to_moviePageFragment)
        }
    }

    private fun onClickClearStory(collectionName: String) {
        if (collectionName == INTERESTED_MOVIES_NAME)
            databaseViewModel.clearInterestedCollection()
        if (collectionName == VIEWED_MOVIES_NAME)
            databaseViewModel.clearViewedCollection()
    }

    private fun onClickAllButton(movieList: List<Movie>, collectionName: String) {
        mainViewModel.setMovieListType(
            MovieCollectionType(
                movieList,
                collectionName
            )
        )
        findNavController().navigate(R.id.action_profilePageFragment_to_moviesListPageFragment)
    }

    private fun onClickCollection(dbUserMovieList: DBUserMovieList) {
        val movieList = mutableListOf<Movie>()
        val movies = dbUserMovieList.movies
        movies.forEach { movieList.add(MovieFromDB(it)) }
        mainViewModel.setMovieListType(
            MovieCollectionType(
                movieList,
                dbUserMovieList.savedMovieList.listName
            )
        )
        findNavController().navigate(R.id.action_profilePageFragment_to_moviesListPageFragment)
    }

    private fun deleteCollectionClick(dbUserMovieList: DBUserMovieList) {
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.deleteMovieList(dbUserMovieList.savedMovieList.listName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfilePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, com.google.android.material.R.anim.abc_fade_in)
        } else {
            AnimationUtils.loadAnimation(context, com.google.android.material.R.anim.abc_fade_out)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val margin = (resources.displayMetrics.scaledDensity * START_END_MARGIN).toInt()
        val spacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()

        //Add decoration
        val decoration = HorizontalItemDecoration(margin, spacing)
        binding.viewedMovies.addDecoration(decoration)
        binding.interestedMovies.addDecoration(decoration)
        binding.collections.addItemDecoration(VerticalItemDecoration(spacing,spacing,margin))

        //Add new collection dialog
        val dialogNewCollectionBinding = DialogNewCollectionBinding.inflate(layoutInflater)
        val dialogNewCollection = Dialog(requireContext())
        dialogNewCollection.setContentView(dialogNewCollectionBinding.root)
        dialogNewCollection.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val addCollectionButton = dialogNewCollectionBinding.doneButton
        val newCollectionName = dialogNewCollectionBinding.editText
        val closeDialogNewCollectionButton = dialogNewCollectionBinding.closeButton
        closeDialogNewCollectionButton.setOnClickListener { dialogNewCollection.hide() }

        //Error dialog
        val dialogErrorBinding = DialogErrorBinding.inflate(layoutInflater)
        val dialogError = Dialog(requireContext())
        dialogError.setContentView(dialogErrorBinding.root)
        dialogError.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val errorDescription = dialogErrorBinding.errorDescription
        val closeDialogErrorButton = dialogErrorBinding.closeButton
        closeDialogErrorButton.setOnClickListener { dialogError.hide() }

        //Add new collection
        binding.addCollection.setOnClickListener {
            dialogNewCollection.show()
            addCollectionButton.setOnClickListener {
                val text = newCollectionName.text.toString()
                if (text.isEmpty()) {
                    errorDescription.text = getString(R.string.no_collection_name)
                    dialogError.show()
                } else if (databaseViewModel.checkMovieListName(text)) {
                    errorDescription.text = getString(R.string.wrong_collection_name)
                    dialogError.show()
                } else viewLifecycleOwner.lifecycleScope.launch {
                    databaseViewModel.insertMovieList(text)
                    dialogNewCollection.hide()
                }
            }
        }

        //Update user collections
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.updateCollections()
        }

        //User collections
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.collections.collect { movieLists ->
                binding.collections.adapter = CollectionAdapter(
                    movieLists,
                    onClick = { onClickCollection(it) },
                    onDeleteIconClick = { deleteCollectionClick(it) })
            }
        }

        //Viewed movies
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.viewedMovies.collect { movies ->
                val size = min(movies.size, 20)
                val movieList = mutableListOf<Movie>()
                for (i in 0 until size) {
                    movieList.add(movies[i])
                }
                val viewedMoviesAdapter = MovieListAdapter(
                    movieList,
                    clearHistory = movies.isNotEmpty(),
                    onClick = { onClickMovie(it) },
                    lastElementClick = { onClickClearStory(VIEWED_MOVIES_NAME) })

                binding.viewedMovies.initView(
                    adapter = viewedMoviesAdapter,
                    onForwardButtonClick = { onClickAllButton(movies, VIEWED_MOVIES_NAME) }
                )
            }
        }

        //Interested movies
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.interestedMovies.collect { movies ->
                val size = min(movies.size, 20)
                val movieList = mutableListOf<Movie>()
                for (i in 0 until size) {
                    movieList.add(movies[i])
                }
                val interestedMoviesAdapter = MovieListAdapter(
                    movieList,
                    clearHistory = movies.isNotEmpty(),
                    onClick = { onClickMovie(it) },
                    lastElementClick = { onClickClearStory(INTERESTED_MOVIES_NAME) })
                binding.interestedMovies.initView(
                    adapter = interestedMoviesAdapter,
                    onForwardButtonClick = { onClickAllButton(movies, INTERESTED_MOVIES_NAME) }
                )
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}