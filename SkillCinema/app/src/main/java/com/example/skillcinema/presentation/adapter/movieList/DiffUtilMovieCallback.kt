package com.example.skillcinema.presentation.adapter.movieList

import androidx.recyclerview.widget.DiffUtil
import com.example.skillcinema.entity.Movie

class DiffUtilMovieCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.nameRu == newItem.nameRu
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }
}