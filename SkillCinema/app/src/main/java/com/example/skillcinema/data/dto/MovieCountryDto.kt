package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_COUNTRY
import com.example.skillcinema.entity.MovieCountry
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCountryDto(
    @Json(name = RESPONSE_PARAMETER_COUNTRY) override val country: String
) : MovieCountry