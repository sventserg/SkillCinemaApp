package com.example.skillcinema.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.skillcinema.App
import com.example.skillcinema.R

class MainActivity : AppCompatActivity() {

    private val databaseViewModel = App.appComponent.databaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.activity_nav_graph)
        if (!databaseViewModel.isOnBoardingNeeded()) {
            graph.setStartDestination(R.id.onBoardingPageFragment)
        } else
            graph.setStartDestination(R.id.mainFragment2)

        navController.graph = graph
    }
}