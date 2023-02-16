package com.example.skillcinema.presentation.adapter.staff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.dto.StaffProfessionKeyDto
import com.example.skillcinema.databinding.ItemStaffPersonBinding
import com.example.skillcinema.entity.Staff
import com.example.skillcinema.presentation.*

class StaffAdapter(
    private val staff: List<Staff>,
    private val onClick: (Staff) -> Unit
) : RecyclerView.Adapter<StaffViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding = ItemStaffPersonBinding.inflate(LayoutInflater.from(parent.context))
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = staff[position]
        holder.binding.name.text = item.name()
        holder.binding.role.text = when (item.profession()) {
            StaffProfessionKeyDto.WRITER -> PROFESSION_WRITER
            StaffProfessionKeyDto.OPERATOR -> PROFESSION_OPERATOR
            StaffProfessionKeyDto.EDITOR -> PROFESSION_EDITOR
            StaffProfessionKeyDto.COMPOSER -> PROFESSION_COMPOSER
            StaffProfessionKeyDto.PRODUCER_USSR -> PROFESSION_PRODUCER_USSR
            StaffProfessionKeyDto.TRANSLATOR -> PROFESSION_TRANSLATOR
            StaffProfessionKeyDto.DIRECTOR -> PROFESSION_DIRECTOR
            StaffProfessionKeyDto.DESIGN -> PROFESSION_DESIGN
            StaffProfessionKeyDto.PRODUCER -> PROFESSION_PRODUCER
            StaffProfessionKeyDto.ACTOR -> item.description
            StaffProfessionKeyDto.VOICE_DIRECTOR -> PROFESSION_VOICE_DIRECTOR
            else -> PROFESSION_UNKNOWN
        }

        holder.binding.root.setOnClickListener {
            onClick(item)
        }

        Glide
            .with(holder.binding.root)
            .load(item.posterUrl)
            .centerCrop()
            .into(holder.binding.photo)
    }

    override fun getItemCount(): Int {
        return staff.size
    }
}