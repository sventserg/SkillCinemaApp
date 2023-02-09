package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_IMAGE_URL
import com.example.skillcinema.data.RESPONSE_PARAMETER_PREVIEW_URL
import com.example.skillcinema.entity.MovieImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieImageDto(
    @Json(name = RESPONSE_PARAMETER_IMAGE_URL) override val imageUrl: String,
    @Json(name = RESPONSE_PARAMETER_PREVIEW_URL) override val previewUrl: String
) : MovieImage