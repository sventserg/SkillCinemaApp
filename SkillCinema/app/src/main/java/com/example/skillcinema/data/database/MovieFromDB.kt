package com.example.skillcinema.data.database

import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.MovieCountry
import com.example.skillcinema.entity.MovieGenre
import com.example.skillcinema.entity.database.DBMovie

class MovieFromDB(
    val movie: DBMovie,
    override val kinopoiskId: Int? = movie.id,
    override val filmId: Int? = movie.id,
    override val nameRu: String? = null,
    override val nameEn: String? = null,
    override val nameOriginal: String? = null,
    override val year: Int? = null,
    override val posterUrl: String = movie.posterUrl,
    override val posterUrlPreview: String = movie.posterUrl,
    override val countries: List<MovieCountry>? = null,
    override val genres: List<MovieGenre>? = null,
    override val filmLength: String? = null,
    override val ratingKinopoisk: Double? = null,
    override val ratingImdb: Double? = null,
    override val slogan: String? = null,
    override val description: String? = null,
    override val shortDescription: String? = null,
    override val ratingAgeLimits: String? = null,
    override val type: String? = null,
    override val rating: String? = null,
    override val logoUrl: String? = null,
    override val coverUrl: String? = null,
    override val webUrl: String? = null
) : Movie {
    override fun descriptionSecondLine(): String {
        TODO("Not yet implemented")
    }

    override fun genresText(): String {
       return movie.genre
    }

    override fun descriptionThirdLine(): String {
        TODO("Not yet implemented")
    }

    override fun id(): Int {
        return movie.id
    }

    override fun rating(): String? {
        return movie.rating
    }

    override fun name(): String {
        return movie.name
    }

}