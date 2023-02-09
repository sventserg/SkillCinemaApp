package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_FILMS
import com.example.skillcinema.data.RESPONSE_PARAMETER_TOTAL
import com.example.skillcinema.entity.MovieList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopMovieListDto(
    @Json(name = RESPONSE_PARAMETER_FILMS) override val movieList: List<MovieDto>?,
    @Json(name = RESPONSE_PARAMETER_TOTAL) override val total: Int?
    ) : MovieList {
}