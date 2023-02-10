package com.example.skillcinema.presentation.adapter.movieImage

import androidx.recyclerview.widget.DiffUtil
import com.example.skillcinema.entity.MovieImage

class DiffUtilMovieImageCallback : DiffUtil.ItemCallback<MovieImage>() {
    override fun areItemsTheSame(oldItem: MovieImage, newItem: MovieImage): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: MovieImage, newItem: MovieImage): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }
}