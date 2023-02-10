package com.example.skillcinema.data.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE

class AppPreferences(private val context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    private val prefEditor = pref?.edit()

    fun isOnBoardingNeeded (): Boolean {
        return pref.getBoolean(ON_BOARDING_PREF_KEY, true)
    }

    fun onBoardingIsOver() {
        prefEditor?.putBoolean(ON_BOARDING_PREF_KEY, false)
        prefEditor?.apply()
    }

    companion object {
       private const val PREF_NAME = "app_settings"
       private const val ON_BOARDING_PREF_KEY = "on_boarding"
    }


}