package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.skillcinema.presentation.viewmodel.adapter.movieList.PagingMovieListAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchPageVM = App.appComponent.searchPageVM()
    private val mainViewModel = App.appComponent.mainViewModel()
    private val databaseViewModel = App.appComponent.databaseViewModel()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.notFound.visibility = View.GONE

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchSettingsFragment)
        }

        val pagingAdapter =
            PagingMovieListAdapter(databaseViewModel.viewedMovies.value) { onClickMovie(it) }
        binding.searchResult.adapter = pagingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            searchPageVM.checkFilters()
        }

        var pagedList: Flow<PagingData<Movie>>
        pagingAdapter.loadStateFlow.onEach {
            if (it.refresh != LoadState.Loading) {
                if (pagingAdapter.snapshot().isEmpty()) {
                    binding.notFound.visibility = View.VISIBLE
                } else binding.notFound.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.inputText.addTextChangedListener {
            val text = binding.inputText.text.toString()
            if (it != null && text.isNotEmpty()) {
                binding.searchResult.visibility = View.VISIBLE
                searchPageVM.setKeyword(text)
                pagedList = searchPageVM.getPagedList(databaseViewModel.viewedMovies.value)
                pagedList.onEach { data ->
                    pagingAdapter.submitData(data)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else binding.searchResult.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.inputText.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}