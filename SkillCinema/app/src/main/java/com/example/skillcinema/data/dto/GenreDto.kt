package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_GENRE
import com.example.skillcinema.data.RESPONSE_PARAMETER_ID
import com.example.skillcinema.entity.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDto (
    @Json(name = RESPONSE_PARAMETER_ID) override val id: Int,
    @Json(name = RESPONSE_PARAMETER_GENRE) override val name: String
) : Genre