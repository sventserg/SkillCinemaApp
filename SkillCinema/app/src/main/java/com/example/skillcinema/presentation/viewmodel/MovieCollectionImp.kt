package com.example.skillcinema.presentation.viewmodel

import com.example.skillcinema.entity.MovieCollection
import com.example.skillcinema.entity.MovieList

class MovieCollectionImp(
    override val name: String,
    override val image: Int,
    override val movieList: MovieList
) : MovieCollection {
}