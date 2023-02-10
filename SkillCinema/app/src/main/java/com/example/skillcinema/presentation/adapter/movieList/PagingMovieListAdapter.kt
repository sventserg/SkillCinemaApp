package com.example.skillcinema.presentation.adapter.movieList

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemMovieBinding
import com.example.skillcinema.entity.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PagingMovieListAdapter(
    private val viewedMovies: List<Movie>? = null,
    private val onClick: (Movie?) -> Unit
) : PagingDataAdapter<Movie, MovieListViewHolder>(DiffUtilMovieCallback()) {

    private val _itemWidth = MutableStateFlow<Int?>(null)
    val itemWidth = _itemWidth.asStateFlow()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return MovieListViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            val res = holder.binding.gradient.resources
            viewedMovies?.forEach {
                if (it.id() == item.id()) {
                    holder.binding.watchedIcon.visibility = View.VISIBLE
                    holder.binding.gradient.background =
                        res.getDrawable(R.drawable.movie_gradient, null)
                }
            }
            holder.binding.root.post {
                val width = holder.binding.container.measuredWidth
                _itemWidth.value = width
                Log.d("Adapter", "Width: $width")
            }
            Glide
                .with(holder.binding.root)
                .load(item.posterUrlPreview)
                .centerCrop()
                .into(holder.binding.moviePosterImage)
            holder.binding.movieName.text = item.name()
            holder.binding.movieGenre.text = item.genresText()
            holder.binding.root.setOnClickListener {
                onClick(item)
            }
            val rating = item.rating()
            if (rating != null) {
                holder.binding.ratingText.text = rating
            } else holder.binding.rating.visibility = View.GONE
        }
    }
}