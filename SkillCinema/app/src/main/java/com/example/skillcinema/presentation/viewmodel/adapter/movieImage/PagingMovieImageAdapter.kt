package com.example.skillcinema.presentation.viewmodel.adapter.movieImage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.MovieImageGalleryPreviewBinding
import com.example.skillcinema.entity.MovieImage

class PagingMovieImageAdapter(
    private val onImageClick: (MovieImage?) -> Unit
) :
    PagingDataAdapter<MovieImage, GalleryMovieImageViewHolder>(DiffUtilMovieImageCallback()) {

    override fun onBindViewHolder(holder: GalleryMovieImageViewHolder, position: Int) {
        val item = getItem(position)

        Glide
            .with(holder.binding.root)
            .load(item?.previewUrl)
            .fitCenter()
            .into(holder.binding.image)
        holder.binding.root.setOnClickListener {
            onImageClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryMovieImageViewHolder {
        val binding = MovieImageGalleryPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryMovieImageViewHolder(binding)
    }
}