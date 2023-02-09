package com.example.skillcinema.presentation.viewmodel.adapter.movieImage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemScrollableImageBinding
import com.example.skillcinema.entity.MovieImage

class ScrollablePagingImageAdapter() :
    PagingDataAdapter<MovieImage, ScrollableImageViewHolder>(DiffUtilMovieImageCallback()) {


    override fun onBindViewHolder(holder: ScrollableImageViewHolder, position: Int) {

        val item = getItem(position)

        Glide
            .with(holder.binding.root)
            .load(item?.previewUrl)
            .fitCenter()
            .into(holder.binding.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollableImageViewHolder {
        val binding =
            ItemScrollableImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScrollableImageViewHolder(binding)
    }
}