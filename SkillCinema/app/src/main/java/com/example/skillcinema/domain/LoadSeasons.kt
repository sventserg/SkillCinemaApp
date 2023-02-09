package com.example.skillcinema.domain

import com.example.skillcinema.entity.Seasons
import com.example.skillcinema.entity.data.repository.LoadSeasonsRepository

class LoadSeasons(private val repository: LoadSeasonsRepository) {
    suspend fun loadSeasons(id: Int): Seasons? {
        return repository.loadSeasons(id)
    }
}