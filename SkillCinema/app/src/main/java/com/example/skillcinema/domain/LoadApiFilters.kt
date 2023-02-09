package com.example.skillcinema.domain

import com.example.skillcinema.entity.ApiFilters
import com.example.skillcinema.entity.data.repository.LoadApiFiltersRepository

class LoadApiFilters(
    private val loadApiFiltersRepository: LoadApiFiltersRepository
) {
    suspend fun execute(): ApiFilters? {
        return loadApiFiltersRepository.loadInfo()
    }
}