package com.example.skillcinema.presentation.viewmodel.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.FilterNameBinding

class MovieFilterAdapter(
    private val filterList: List<String>,
    private val onClick: (String) -> Unit
) :
    RecyclerView.Adapter<MovieFilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFilterViewHolder {
        val binding = FilterNameBinding.inflate(LayoutInflater.from(parent.context))
        return MovieFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieFilterViewHolder, position: Int) {
        val item = filterList[position]

        holder.binding.filterName.text = item

        holder.binding.root.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }
}