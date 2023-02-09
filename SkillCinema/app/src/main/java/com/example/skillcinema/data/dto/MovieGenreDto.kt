package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_GENRE
import com.example.skillcinema.entity.MovieGenre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenreDto(
    @Json(name = RESPONSE_PARAMETER_GENRE) override val genre: String
) : MovieGenre