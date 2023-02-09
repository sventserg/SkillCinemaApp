package com.example.skillcinema.presentation.viewmodel.adapter.addMovieToCollection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemAddCollectionBinding
import com.example.skillcinema.databinding.ItemAddCollectionBottomBinding
import com.example.skillcinema.databinding.ItemAddCollectionTopBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.data.database.DBUserMovieList
import com.example.skillcinema.presentation.*
import com.example.skillcinema.presentation.viewmodel.DatabaseViewModel
import java.util.*

class AddMovieToCollectionAdapterOLD(
    private val movie: Movie,
    private val databaseViewModel: DatabaseViewModel,
    private val clickAddNewCollection: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = mutableListOf<Any>()

    init {
        list.add(TOP_ELEMENT)
        databaseViewModel.collections.value.forEach {
            list.add(it)
        }
        list.add(BOTTOM_ELEMENT)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TOP_ELEMENT_TYPE -> {
                AddMovieToCollectionTopViewHolder(
                    ItemAddCollectionTopBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            BOTTOM_ELEMENT_TYPE -> {
                AddMovieToCollectionBottomViewHolder(
                    ItemAddCollectionBottomBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                AddMovieToCollectionViewHolder(
                    ItemAddCollectionBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list[position]

        when (holder) {
            is AddMovieToCollectionViewHolder -> {
                if (item is DBUserMovieList) {
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
                            databaseViewModel.saveMovieToCollection(movie, item.savedMovieList.listName, Date().time)
                        }
                        list.clear()
                        list.add(TOP_ELEMENT)
                        databaseViewModel.collections.value.forEach {
                            list.add(it)
                        }
                        list.add(BOTTOM_ELEMENT)
                        notifyDataSetChanged()
                    }
                }

            }
            is AddMovieToCollectionBottomViewHolder -> {
                holder.binding.root.setOnClickListener {
                    clickAddNewCollection()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            TOP_ELEMENT -> TOP_ELEMENT_TYPE
            BOTTOM_ELEMENT -> BOTTOM_ELEMENT_TYPE
            else -> COLLECTION_ELEMENT_TYPE
        }
    }

    private fun checkMovie(item: DBUserMovieList): Int {
        return if (item.checkMovie(movie)) R.drawable.checked_box
        else R.drawable.check_box
    }

    companion object {
        private const val TOP_ELEMENT = "top"
        private const val BOTTOM_ELEMENT = "bottom"
        private const val TOP_ELEMENT_TYPE = 0
        private const val COLLECTION_ELEMENT_TYPE = 1
        private const val BOTTOM_ELEMENT_TYPE = 2
    }
}