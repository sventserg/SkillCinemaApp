package com.example.skillcinema.presentation.adapter.episode

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemEpisodeBinding
import com.example.skillcinema.entity.Episode
import java.text.SimpleDateFormat
import java.util.*

class EpisodeAdapter(
    private val episodeList: List<Episode>,
    private val context: Context
) : RecyclerView.Adapter<EpisodeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context))
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = episodeList[position]
        val name = item.nameRu ?: item.nameEn ?:""
        val releaseDate = item.releaseDate
        val responseFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        if (releaseDate != null) {
            val date = responseFormat.parse(releaseDate)
            val dateText = date?.let { dateFormat.format(it) }
            holder.binding.date.text = dateText
        }

        holder.binding.name.text =
            context.getString(R.string.episode_name, item.episodeNumber, name)
        Log.d("Adapter", "${holder.binding.name.text}")
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }
}