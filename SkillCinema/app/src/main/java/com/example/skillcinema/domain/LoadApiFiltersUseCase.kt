package com.example.skillcinema.domain

import com.example.skillcinema.entity.ApiFilters
import com.example.skillcinema.entity.repository.LoadApiFiltersRepository

class LoadApiFiltersUseCase(
    private val loadApiFiltersRepository: LoadApiFiltersRepository
) {
    suspend fun execute(): ApiFilters? {
        return loadApiFiltersRepository.loadInfo()
    }
}