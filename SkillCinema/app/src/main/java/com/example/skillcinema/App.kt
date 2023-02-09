package com.example.skillcinema

import android.app.Application
import androidx.room.Room
import com.example.skillcinema.data.database.SavedMoviesDatabase
import com.example.skillcinema.di.AppComponent
import com.example.skillcinema.di.DaggerAppComponent
import com.example.skillcinema.di.DataModule

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
        lateinit var savedMoviesDB: SavedMoviesDatabase
    }

    override fun onCreate() {
        super.onCreate()
//        savedMoviesDB = Room.databaseBuilder(
//            applicationContext,
//            SavedMoviesDatabase::class.java,
//            "saved_movies_db"
//        )
//            .fallbackToDestructiveMigration()
//            .build()
        appComponent = DaggerAppComponent.builder()
            .dataModule(DataModule(this))
            .build()
            }

}