package com.example.skillcinema.presentation.adapter.addMovieToCollection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemAddCollectionBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.database.DBUserMovieList
import com.example.skillcinema.presentation.viewmodel.DatabaseViewModel
import java.util.*

class AddMovieToCollectionAdapter(
    private val movie: Movie,
    private val databaseViewModel: DatabaseViewModel
) :
    RecyclerView.Adapter<AddMovieToCollectionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddMovieToCollectionViewHolder {
        return AddMovieToCollectionViewHolder(
            ItemAddCollectionBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AddMovieToCollectionViewHolder, position: Int) {

        val item = databaseViewModel.collections.value[position]

        holder.binding.collectionName.text = item.savedMovieList.listName
        holder.binding.collectionSize.text = item.movies.size.toString()
        holder.binding.checkbox.setImageResource(checkMovie(item))
        holder.binding.checkbox.setOnClickListener {
            if (item.checkMovie(movie)) {
                databaseViewModel.deleteMovieFromCollection(
                    movie.id(),
                    item.savedMovieList.listName
                )
            } else {
                val currentTime = Date().time
                databaseViewModel.saveMovieToCollection(movie, item.savedMovieList.listName, currentTime)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return databaseViewModel.collections.value.size
    }


    private fun checkMovie(item: DBUserMovieList): Int {
        return if (item.checkMovie(movie)) R.drawable.checked_box
        else R.drawable.check_box
    }

}