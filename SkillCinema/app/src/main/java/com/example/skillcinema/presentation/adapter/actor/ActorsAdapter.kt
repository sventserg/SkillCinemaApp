package com.example.skillcinema.presentation.adapter.actor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.*
import com.example.skillcinema.databinding.ItemActorBinding
import com.example.skillcinema.entity.Staff
import com.example.skillcinema.presentation.*

class ActorsAdapter(
    private val actors: List<Staff>,
    private val onClick: (Staff) -> Unit
) : RecyclerView.Adapter<ActorsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context))
        return ActorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val item = actors[position]
        val description = item.description
        val professionKey = item.professionKey
        val name: String = item.nameRu ?: (item.nameEn ?: NO_NAME)

        var role : String? = null
        when (professionKey) {
            PROFESSION_KEY_WRITER -> role = PROFESSION_WRITER
            PROFESSION_KEY_OPERATOR -> role = PROFESSION_OPERATOR
            PROFESSION_KEY_EDITOR -> role = PROFESSION_EDITOR
            PROFESSION_KEY_COMPOSER -> role = PROFESSION_COMPOSER
            PROFESSION_KEY_PRODUCER_USSR -> role = PROFESSION_PRODUCER_USSR
            PROFESSION_KEY_TRANSLATOR -> role = PROFESSION_TRANSLATOR
            PROFESSION_KEY_DIRECTOR -> role = PROFESSION_DIRECTOR
            PROFESSION_KEY_DESIGN -> role = PROFESSION_DESIGN
            PROFESSION_KEY_PRODUCER -> role = PROFESSION_PRODUCER
            PROFESSION_KEY_ACTOR -> role = description
            PROFESSION_KEY_VOICE_DIRECTOR -> role = PROFESSION_VOICE_DIRECTOR
            PROFESSION_KEY_UNKNOWN -> role = PROFESSION_UNKNOWN
        }

        holder.binding.name.text = name
        holder.binding.role.text = role?: ""

        Glide
            .with(holder.binding.root)
            .load(item.posterUrl)
            .fitCenter()
            .into(holder.binding.photo)

        holder.binding.root.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return actors.size
    }
}