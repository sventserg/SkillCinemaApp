package com.example.skillcinema.entity.data.repository

import com.example.skillcinema.entity.Seasons

interface LoadSeasonsRepository {
    suspend fun loadSeasons(id: Int): Seasons?
}