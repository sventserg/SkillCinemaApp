package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSearchBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.adapter.movieList.PagingMovieListAdapter
import com.example.skillcinema.presentation.decorator.SimpleVerticalItemDecoration
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchPageVM = App.appComponent.searchPageVM()
    private val mainViewModel = App.appComponent.mainViewModel()
    private val databaseViewModel = App.appComponent.databaseViewModel()
    private val pagingAdapter =
        PagingMovieListAdapter(databaseViewModel.viewedMovies.value) { onClickMovie(it) }

    private fun onClickMovie(movie: Movie?) {
        if (movie != null) {
            mainViewModel.clickOnMovie(movie)
            findNavController().navigate(R.id.action_searchFragment_to_moviePageFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater)
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

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchSettingsFragment)
        }

        binding.searchResult.adapter = pagingAdapter
        binding.searchResult.addItemDecoration(SimpleVerticalItemDecoration(spacing, margin))

        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.checkFilters()
        }

        pagingAdapter.loadStateFlow.onEach {
            if (it.refresh == LoadState.Loading) {
                binding.searchProgress.visibility = View.VISIBLE
            } else {
                binding.searchProgress.visibility = View.INVISIBLE
                if (pagingAdapter.itemCount == 0) {
                    binding.notFound.visibility = View.VISIBLE
                } else {
                    binding.notFound.visibility = View.GONE
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.pagedSearchList.collect {
                it?.onEach { data ->
                    pagingAdapter.submitData(data)
                }?.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes, such as NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                // Scroll to top is synchronous with UI updates, even if remote load was
                // triggered.
                .collect { binding.searchResult.scrollToPosition(0) }
        }

        binding.inputText.addTextChangedListener {
            binding.searchResult.visibility = View.GONE
            val text = binding.inputText.text.toString()
            if (it != null && text.isNotEmpty()) {
                searchPageVM.setKeyword(text, databaseViewModel.viewedMovies.value)
                binding.searchResult.scrollToPosition(0)
                binding.searchResult.visibility = View.VISIBLE
            } else binding.searchResult.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        binding.searchResult.adapter = null
        _binding = null
        super.onDestroyView()
    }

}