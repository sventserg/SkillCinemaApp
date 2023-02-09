package com.example.skillcinema.presentation.viewmodel

import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.MovieList

class MovieListImp(
    override val movieList: List<Movie>?,
    override val total: Int?
    ) : MovieList {
}