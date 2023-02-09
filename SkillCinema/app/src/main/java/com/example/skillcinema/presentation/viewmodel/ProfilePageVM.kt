package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skillcinema.domain.LoadMovieData
import com.example.skillcinema.entity.Movie

class ProfilePageVM(
    private val loadMovieData: LoadMovieData
) : ViewModel() {

   suspend fun loadMovie(movieID: Int): Movie? {
       return loadMovieData.loadMovie(movieID)
    }

}