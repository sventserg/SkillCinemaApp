package com.example.skillcinema.data.dto

import android.util.Log
import com.example.skillcinema.data.*
import com.example.skillcinema.entity.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class MovieDto(
    @Json(name = RESPONSE_PARAMETER_KINOPOISK_ID) override val kinopoiskId: Int?,
    @Json(name = RESPONSE_PARAMETER_FILM_ID) override val filmId: Int?,
    @Json(name = RESPONSE_PARAMETER_NAME_RU) override val nameRu: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_EN) override val nameEn: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_ORIGINAL) override val nameOriginal: String?,
    @Json(name = RESPONSE_PARAMETER_YEAR) override val year: Int?,
    @Json(name = RESPONSE_PARAMETER_POSTER_URL) override val posterUrl: String,
    @Json(name = RESPONSE_PARAMETER_POSTER_URL_PREVIEW) override val posterUrlPreview: String,
    @Json(name = RESPONSE_PARAMETER_COUNTRIES) override val countries: List<MovieCountryDto>?,
    @Json(name = RESPONSE_PARAMETER_GENRES) override val genres: List<MovieGenreDto>?,
    @Json(name = RESPONSE_PARAMETER_FILM_LENGTH) override val filmLength: String?,
    @Json(name = RESPONSE_PARAMETER_RATING_KINOPOISK) override val ratingKinopoisk: Double?,
    @Json(name = RESPONSE_PARAMETER_RATING_IMDB) override val ratingImdb: Double?,
    @Json(name = RESPONSE_PARAMETER_SLOGAN) override val slogan: String?,
    @Json(name = RESPONSE_PARAMETER_DESCRIPTION) override val description: String?,
    @Json(name = RESPONSE_PARAMETER_SHORT_DESCRIPTION) override val shortDescription: String?,
    @Json(name = RESPONSE_PARAMETER_RATING_AGE_LIMITS) override val ratingAgeLimits: String?,
    @Json(name = RESPONSE_PARAMETER_TYPE) override val type: String?,
    @Json(name = RESPONSE_PARAMETER_RATING) override val rating: String?,
    @Json(name = RESPONSE_PARAMETER_LOGO_URL) override val logoUrl: String?,
    @Json(name = RESPONSE_PARAMETER_COVER_URL) override val coverUrl: String?,
    @Json(name = RESPONSE_WEB_COVER_URL) override val webUrl: String?

) : Movie {
    override fun descriptionSecondLine(): String {
        var text = ""
        if (year != null) text += "$year, "
        text += genresText()
        Log.d("Second line", text)
        return text
    }

    override fun genresText(): String {
        var text = ""
        genres?.forEach {
            text += it.genre
            text += ", "
        }
        if (text != "") {
            text = text.substring(0, text.length - 2)
        }
        return text
    }

    override fun descriptionThirdLine(): String {
        var thirdLine = ""

        countries?.forEach {
            thirdLine += if (it == countries?.last()) it.country else "${it.country}, "
        }
        Log.d("ThirdLine", thirdLine)

        val lengthString = filmLength ?: ""
        val filmLengthString = if (lengthString.contains(":")) {
            lengthString.substring(0, 2) + "ч " + lengthString.substring(3) + "мин"
        } else if (lengthString != "") {
            "${lengthString.toInt() / 60}ч + ${lengthString.toInt() % 60}мин"
        } else {
            ""
        }
        if (filmLengthString != "") {
            thirdLine += if (thirdLine != "") ", $filmLengthString" else
                filmLengthString
        }

        Log.d("ThirdLine", thirdLine)

        val age = ratingAgeLimits?.replace("age", "") + "+"
        if (ratingAgeLimits != null) {
            thirdLine += " , $age"
        }

        Log.d("ThirdLine", thirdLine)
        return thirdLine
    }


    override fun id(): Int {
        return kinopoiskId ?: filmId ?: NO_MOVIE_ID
    }

    override fun rating(): String? {
        return ratingKinopoisk?.toString() ?: rating
    }

    override fun name(): String {
        return nameRu ?: nameEn ?: nameOriginal ?: "Нет названия"
    }
}