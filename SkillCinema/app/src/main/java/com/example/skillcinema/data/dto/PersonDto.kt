package com.example.skillcinema.data.dto

import com.example.skillcinema.data.*
import com.example.skillcinema.entity.Person
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PersonDto(
    @Json(name = RESPONSE_PARAMETER_PERSON_ID) override val personId: Int,
    @Json(name = RESPONSE_PARAMETER_WEB_URL) override val webUrl: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_RU) override val nameRu: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_EN) override val nameEn: String?,
    @Json(name = RESPONSE_PARAMETER_SEX) override val sex: String?,
    @Json(name = RESPONSE_PARAMETER_POSTER_URL) override val posterUrl: String,
    @Json(name = RESPONSE_PARAMETER_GROWTH) override val growth: String?,
    @Json(name = RESPONSE_PARAMETER_BIRTHDAY) override val birthday: String?,
    @Json(name = RESPONSE_PARAMETER_DEATH) override val death: String?,
    @Json(name = RESPONSE_PARAMETER_AGE) override val age: Int?,
    @Json(name = RESPONSE_PARAMETER_BIRTH_PLACE) override val birthPlace: String?,
    @Json(name = RESPONSE_PARAMETER_DEATH_PLACE) override val deathPlace: String?,
    @Json(name = RESPONSE_PARAMETER_HAS_AWARDS) override val hasAwards: Int?,
    @Json(name = RESPONSE_PARAMETER_PROFESSION) override val profession: String?,
    @Json(name = RESPONSE_PARAMETER_FACTS) override val facts: List<String>?,
    @Json(name = RESPONSE_PARAMETER_FILMS) override val films: List<PersonMovieDto>
) : Person {
    override fun name(): String {
        var name = ""
        if (nameRu != null && nameRu.isNotEmpty()) name = nameRu else {
            if (nameEn != null && nameEn.isNotEmpty()) name = nameEn
        }
        return name
    }
}