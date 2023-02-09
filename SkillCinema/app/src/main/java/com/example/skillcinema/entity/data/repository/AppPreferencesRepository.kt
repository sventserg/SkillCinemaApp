package com.example.skillcinema.entity.data.repository


interface AppPreferencesRepository {
    fun isOnBoardingNeeded(): Boolean

    fun onBoardingIsOver()
}