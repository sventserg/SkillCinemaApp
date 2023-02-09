package com.example.skillcinema.presentation.viewmodel.adapter.movieImage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.MovieImagePreviewBinding
import com.example.skillcinema.entity.MovieImage

class MovieImageAdapter(
    private val gallery: List<MovieImage>,
    private val clickOnImage: (MovieImage) -> Unit
) : RecyclerView.Adapter<MovieImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImageViewHolder {
        val binding = MovieImagePreviewBinding.inflate(LayoutInflater.from(parent.context))
        return MovieImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieImageViewHolder, position: Int) {
        val item = gallery[position]

        Glide
            .with(holder.binding.root)
            .load(item.imageUrl)
            .fitCenter()
            .into(holder.binding.image)

        holder.binding.root.setOnClickListener { clickOnImage(item) }
    }


    override fun getItemCount(): Int {
        return gallery.size
    }
}