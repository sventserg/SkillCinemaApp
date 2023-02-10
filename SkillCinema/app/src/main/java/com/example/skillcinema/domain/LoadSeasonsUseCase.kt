package com.example.skillcinema.domain

import com.example.skillcinema.entity.Seasons
import com.example.skillcinema.entity.repository.LoadSeasonsRepository

class LoadSeasonsUseCase(private val repository: LoadSeasonsRepository) {
    suspend fun loadSeasons(id: Int): Seasons? {
        return repository.loadSeasons(id)
    }
}