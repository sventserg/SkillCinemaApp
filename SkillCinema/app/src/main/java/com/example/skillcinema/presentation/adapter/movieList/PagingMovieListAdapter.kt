package com.example.skillcinema.presentation.adapter.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemMovieBinding
import com.example.skillcinema.entity.Movie

class PagingMovieListAdapter(
    private val viewedMovies: List<Movie>? = null,
    private val onClick: (Movie?) -> Unit
) : PagingDataAdapter<Movie, MovieListViewHolder>(DiffUtilMovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            val res = holder.binding.gradient.resources
            var isItemInViewed = false

            viewedMovies?.forEach {
                if (it.id() == item.id()) {
                    isItemInViewed = true
                }
                if (isItemInViewed) {
                    holder.binding.watchedIcon.visibility = View.VISIBLE
                    holder.binding.gradient.background =getDrawable(res, R.drawable.movie_gradient, null)
                } else {
                    holder.binding.watchedIcon.visibility = View.GONE
                    holder.binding.gradient.background = null
                }
            }

            Glide
                .with(holder.binding.root)
                .load(item.posterUrlPreview)
                .centerCrop()
                .into(holder.binding.moviePosterImage)
            holder.binding.movieName.text = item.name()
            holder.binding.movieGenre.text = item.genresText()
            holder.binding.moviePosterImage.setOnClickListener {
                onClick(item) }

            holder.binding.movieName.maxLines = MAX_LINES_DEFAULT
            holder.binding.movieGenre.maxLines = MAX_LINES_DEFAULT

            holder.binding.movieDescription.setOnClickListener {
                when (holder.binding.movieName.maxLines) {
                    MAX_LINES_DEFAULT -> {
                        holder.binding.movieName.maxLines = MAX_LINES_EXPENDED
                        holder.binding.movieGenre.maxLines = MAX_LINES_EXPENDED
                    }
                    MAX_LINES_EXPENDED -> {
                        holder.binding.movieName.maxLines = MAX_LINES_DEFAULT
                        holder.binding.movieGenre.maxLines = MAX_LINES_DEFAULT
                    }
                }
            }

            val rating = item.rating()
            if (rating != null) {
                holder.binding.ratingText.text = rating
            } else holder.binding.rating.visibility = View.GONE
        }
    }

    companion object {
        private const val MAX_LINES_DEFAULT = 2
        private const val MAX_LINES_EXPENDED = 10
    }

}