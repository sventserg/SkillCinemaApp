package com.example.skillcinema.data.repository

import android.content.Context
import com.example.skillcinema.data.preferences.AppPreferences
import com.example.skillcinema.entity.repository.AppPreferencesRepository

class AppPreferencesRepositoryImp(private val context: Context) : AppPreferencesRepository {
    private val appReferences = AppPreferences(context)

    override fun isOnBoardingNeeded(): Boolean {
        return appReferences.isOnBoardingNeeded()
    }

    override fun onBoardingIsOver() {
        appReferences.onBoardingIsOver()
    }

}