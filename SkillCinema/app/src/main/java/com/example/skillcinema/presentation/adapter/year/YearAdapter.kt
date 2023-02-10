package com.example.skillcinema.presentation.adapter.year

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.YearItemBinding

class YearAdapter(
    private val yearList: List<Int>
) : RecyclerView.Adapter<YearItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearItemViewHolder {
        val binding = YearItemBinding.inflate(LayoutInflater.from(parent.context))
        return YearItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YearItemViewHolder, position: Int) {
        val item = yearList[position]

        holder.binding.year.text = item.toString()
    }

    override fun getItemCount(): Int {
        return yearList.size
    }
}