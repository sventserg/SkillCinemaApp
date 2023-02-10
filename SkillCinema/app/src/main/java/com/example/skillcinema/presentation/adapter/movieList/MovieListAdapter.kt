package com.example.skillcinema.presentation.adapter.movieList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemClearHistoryBinding
import com.example.skillcinema.databinding.ItemForwardArrowBinding
import com.example.skillcinema.databinding.ItemMovieBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.*

class MovieListAdapter(
    movieList: List<Movie>,
    forwardArrow: Boolean = false,
    clearHistory: Boolean = false,
    private val onClick: (Movie) -> Unit,
    private val lastElementClick: () -> Unit,
    private val viewedMovies: List<Movie>? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<Any>()

    init {
        movieList.forEach { list.add(it) }
        if (forwardArrow) list.add(FORWARD_ARROW) else if (clearHistory) list.add(CLEAR_HISTORY)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MOVIE_LIST_ADAPTER_FORWARD_ARROW_TYPE -> ForwardArrowViewHolder(
                ItemForwardArrowBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            MOVIE_LIST_ADAPTER_CLEAR_HISTORY_TYPE -> ClearHistoryViewHolder(
                ItemClearHistoryBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            else -> MovieListViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        when (holder) {
            is MovieListViewHolder -> {
                if (item is Movie) {

                    val res = holder.binding.gradient.resources

                    viewedMovies?.forEach {
                        if (it.id() == item.id()) {
                            holder.binding.watchedIcon.visibility = View.VISIBLE
                            holder.binding.gradient.background =
                                res.getDrawable(R.drawable.movie_gradient, null)
                        } else {
//                            holder.binding.watchedIcon.visibility = View.GONE
//                            holder.binding.gradient.background = null
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
                        onClick(item)
                    }

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
                        holder.binding.ratingText.text = item.rating()
                    } else holder.binding.rating.visibility = View.GONE

                }
            }
            is ForwardArrowViewHolder -> {
                holder.binding.forwardButton.setOnClickListener { lastElementClick() }
            }
            is ClearHistoryViewHolder -> {
                holder.binding.clearHistoryButton.setOnClickListener { lastElementClick() }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            FORWARD_ARROW -> MOVIE_LIST_ADAPTER_FORWARD_ARROW_TYPE
            CLEAR_HISTORY -> MOVIE_LIST_ADAPTER_CLEAR_HISTORY_TYPE
            else -> MOVIE_LIST_ADAPTER_MOVIE_LIST_TYPE
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    companion object {
        private const val MAX_LINES_DEFAULT = 2
        private const val MAX_LINES_EXPENDED = 10
    }
}