package com.example.skillcinema.entity

interface Movie {
    val kinopoiskId: Int?
    val filmId: Int?
    val nameRu: String?
    val nameEn: String?
    val nameOriginal: String?
    val year: Int?
    val posterUrl: String
    val posterUrlPreview: String
    val countries: List<MovieCountry>?
    val genres: List<MovieGenre>?
    val filmLength: String?
    val ratingKinopoisk: Double?
    val ratingImdb: Double?
    val slogan: String?
    val description: String?
    val shortDescription: String?
    val ratingAgeLimits: String?
    val type: String?
    val rating: String?
    val logoUrl: String?
    val coverUrl: String?
    val webUrl: String?

    fun descriptionSecondLine(): String
    fun genresText(): String
    fun descriptionThirdLine(): String
    fun id(): Int
    fun rating(): String?
    fun name(): String
}