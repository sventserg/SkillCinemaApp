package com.example.skillcinema.domain

import com.example.skillcinema.entity.data.repository.AppPreferencesRepository

class GetAppPreferences(
    private val repository: AppPreferencesRepository
) {
    fun isOnBoardingNeeded(): Boolean {
        return repository.isOnBoardingNeeded()
    }

    fun onBoardingIsOver() {
        repository.onBoardingIsOver()
    }
}