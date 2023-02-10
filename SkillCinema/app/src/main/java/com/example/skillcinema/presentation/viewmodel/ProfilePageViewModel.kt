package com.example.skillcinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.entity.Movie

class ProfilePageViewModel(
    private val loadMovieData: LoadMovieDataUseCase
) : ViewModel() {

   suspend fun loadMovie(movieID: Int): Movie? {
       return loadMovieData.loadMovie(movieID)
    }

}