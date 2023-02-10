package com.example.skillcinema.domain

import com.example.skillcinema.entity.repository.AppPreferencesRepository

class GetAppPreferencesUseCase(
    private val repository: AppPreferencesRepository
) {
    fun isOnBoardingNeeded(): Boolean {
        return repository.isOnBoardingNeeded()
    }

    fun onBoardingIsOver() {
        repository.onBoardingIsOver()
    }
}