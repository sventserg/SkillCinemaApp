package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_ITEMS
import com.example.skillcinema.data.RESPONSE_PARAMETER_TOTAL
import com.example.skillcinema.entity.MovieImageList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieImageListDto(
    @Json(name = RESPONSE_PARAMETER_ITEMS) override val imageList: List<MovieImageDto>?,
    @Json(name = RESPONSE_PARAMETER_TOTAL) override val total: Int
) : MovieImageList