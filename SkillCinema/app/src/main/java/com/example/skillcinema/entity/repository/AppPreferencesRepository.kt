package com.example.skillcinema.entity.repository


interface AppPreferencesRepository {
    fun isOnBoardingNeeded(): Boolean

    fun onBoardingIsOver()
}