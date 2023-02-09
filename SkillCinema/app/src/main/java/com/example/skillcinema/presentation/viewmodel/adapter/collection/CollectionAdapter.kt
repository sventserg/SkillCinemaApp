package com.example.skillcinema.presentation.viewmodel.adapter.collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.data.database.*
import com.example.skillcinema.databinding.CollectionPreviewBinding
import com.example.skillcinema.entity.data.database.DBUserMovieList

class CollectionAdapter(
    private val collections: List<DBUserMovieList>,
    private val onClick: (DBUserMovieList) -> Unit,
    private val onDeleteIconClick: (DBUserMovieList) -> Unit
) :
    RecyclerView.Adapter<CollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding = CollectionPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = collections[position]

        if (item.savedMovieList.listName == FAVORITES_MOVIES_NAME || item.savedMovieList.listName == WANT_TO_WATCH_MOVIES_NAME) {
            holder.binding.deleteButton.visibility = View.GONE
        }

        holder.binding.collectionName.text = item.savedMovieList.listName
        val size = item.movies.size
        holder.binding.movieNumbers.text = size.toString()
        when (item.savedMovieList.listName) {
            FAVORITES_MOVIES_NAME -> holder.binding.image.setImageResource(R.drawable.favorite_icon)
            WANT_TO_WATCH_MOVIES_NAME -> holder.binding.image.setImageResource(R.drawable.want_to_watch_icon)
            else -> holder.binding.image.setImageResource(R.drawable.profile_icon)
        }

        holder.binding.root.setOnClickListener {
            onClick(item)
        }
        holder.binding.deleteButton.setOnClickListener { onDeleteIconClick(item) }
    }

    override fun getItemCount(): Int {
        return collections.size
    }
}