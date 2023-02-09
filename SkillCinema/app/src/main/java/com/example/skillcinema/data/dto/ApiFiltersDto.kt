package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_COUNTRIES
import com.example.skillcinema.data.RESPONSE_PARAMETER_GENRES
import com.example.skillcinema.entity.ApiFilters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiFiltersDto(
    @Json(name = RESPONSE_PARAMETER_GENRES)
    override val genres: List<GenreDto>,
    @Json(name = RESPONSE_PARAMETER_COUNTRIES)
    override val countries: List<CountryDto>
) : ApiFilters