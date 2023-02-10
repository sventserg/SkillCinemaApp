package com.example.skillcinema.entity.repository

import com.example.skillcinema.entity.ApiFilters

interface LoadApiFiltersRepository {
    suspend fun loadInfo(): ApiFilters?
}