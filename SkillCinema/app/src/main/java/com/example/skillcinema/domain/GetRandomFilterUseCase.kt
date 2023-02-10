package com.example.skillcinema.domain

import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import kotlin.random.Random

class GetRandomFilterUseCase {
    fun getRandomCountry(countries: List<Country>?): Country? {
        return if (countries != null) {
            countries[Random.nextInt(0, countries.size - 1)]
        } else null
    }

    fun getRandomGenre(genres: List<Genre>?): Genre? {
        return if (genres != null) {
            genres[Random.nextInt(0, genres.size - 1)]
        } else null
    }
}