package com.example.skillcinema.presentation.adapter.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.*
import com.example.skillcinema.databinding.ItemMovieBinding
import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.presentation.*

class StaffMovieAdapter(
    private val movieList: List<PersonMovie>
) : RecyclerView.Adapter<MovieListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = movieList[position]
        val nameRu = item.nameRu
        val nameEn = item.nameEn
        val description = item.description
        val rating = item.rating
        val professionKey = item.professionKey

        holder.binding.movieName.text = nameRu ?: nameEn

        if (description != null) {
            holder.binding.movieGenre.text = description
        } else {
            holder.binding.movieGenre.text = when (professionKey) {
                PROFESSION_KEY_WRITER -> PROFESSION_WRITER
                PROFESSION_KEY_OPERATOR -> PROFESSION_OPERATOR
                PROFESSION_KEY_EDITOR -> PROFESSION_EDITOR
                PROFESSION_KEY_COMPOSER -> PROFESSION_COMPOSER
                PROFESSION_KEY_PRODUCER_USSR -> PROFESSION_PRODUCER_USSR
                PROFESSION_KEY_HIMSELF -> PROFESSION_HIMSELF
                PROFESSION_KEY_HERSELF -> PROFESSION_HERSELF
                PROFESSION_KEY_HRONO_TITR_MALE -> PROFESSION_HRONO_TITR_MALE
                PROFESSION_KEY_HRONO_TITR_FEMALE -> PROFESSION_HRONO_TITR_FEMALE
                PROFESSION_KEY_TRANSLATOR -> PROFESSION_TRANSLATOR
                PROFESSION_KEY_DIRECTOR -> PROFESSION_DIRECTOR
                PROFESSION_KEY_DESIGN -> PROFESSION_DESIGN
                PROFESSION_KEY_PRODUCER -> PROFESSION_PRODUCER
                PROFESSION_KEY_ACTOR -> PROFESSION_ACTOR
                PROFESSION_KEY_VOICE_DIRECTOR -> PROFESSION_VOICE_DIRECTOR
                else -> PROFESSION_UNKNOWN
            }
        }
        holder.binding.ratingText.text = rating ?: ""

        Glide
            .with(holder.binding.root)
            .load(item.posterUrl)
            .fitCenter()
            .into(holder.binding.moviePosterImage)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}