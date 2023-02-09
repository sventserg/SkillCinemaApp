package com.example.skillcinema.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.skillcinema.App
import com.example.skillcinema.R

class MainActivity : AppCompatActivity() {

    private val databaseViewModel = App.appComponent.databaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        window.statusBarColor = Color.TRANSPARENT

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val pref = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val isItFirstLaunch = pref.getBoolean(PREF_KEY, true)

        if (!databaseViewModel.isOnBoardingNeeded()) {
            navController.navigate(R.id.action_onBoardingPageFragment_to_mainFragment2)
        }
    }

    companion object {
        const val PREFERENCE_NAME = "pref_name"
        const val PREF_KEY = "on_boarding"
    }
}