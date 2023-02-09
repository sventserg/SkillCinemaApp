package com.example.skillcinema.entity.data.repository

import com.example.skillcinema.entity.ApiFilters

interface LoadApiFiltersRepository {
    suspend fun loadInfo(): ApiFilters?
}