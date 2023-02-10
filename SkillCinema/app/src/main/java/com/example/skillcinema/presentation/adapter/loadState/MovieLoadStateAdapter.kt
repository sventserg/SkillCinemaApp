package com.example.skillcinema.presentation.adapter.loadState

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.skillcinema.databinding.MovieLoadStateBinding

class MovieLoadStateAdapter : LoadStateAdapter<MovieLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) = Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        val binding = MovieLoadStateBinding.inflate(LayoutInflater.from(parent.context))
        return MovieLoadStateViewHolder(binding)
    }
}