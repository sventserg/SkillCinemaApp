package com.example.skillcinema.data

import android.content.Context
import com.example.skillcinema.R
import com.example.skillcinema.entity.MovieImageType

enum class MovieImageTypeImp(override val type: String) : MovieImageType {
    STILL(TYPE_IMAGES_STILL),
    SHOOTING(TYPE_IMAGES_SHOOTING),
    POSTER(TYPE_IMAGES_POSTER),
    FAN_ART(TYPE_IMAGES_FAN_ART),
    PROMO(TYPE_IMAGES_PROMO),
    CONCEPT(TYPE_IMAGES_CONCEPT),
    WALLPAPER(TYPE_IMAGES_WALLPAPER),
    COVER(TYPE_IMAGES_COVER),
    SCREENSHOT(TYPE_IMAGES_SCREENSHOT)
}

class MovieImageTypeList {
    val movieImageTypeList = listOf(
        MovieImageTypeImp.STILL,
        MovieImageTypeImp.SHOOTING,
        MovieImageTypeImp.POSTER,
        MovieImageTypeImp.FAN_ART,
        MovieImageTypeImp.PROMO,
        MovieImageTypeImp.CONCEPT,
        MovieImageTypeImp.WALLPAPER,
        MovieImageTypeImp.COVER,
        MovieImageTypeImp.SCREENSHOT
    )
}

class GetMovieImageTypeRuName {
    fun getMovieImageTypeRuName(context: Context, movieImageType: MovieImageType): String {
        return when (movieImageType) {
            MovieImageTypeImp.STILL -> context.getString(R.string.still)
            MovieImageTypeImp.SHOOTING -> context.getString(R.string.shooting)
            MovieImageTypeImp.POSTER -> context.getString(R.string.poster)
            MovieImageTypeImp.FAN_ART -> context.getString(R.string.fan_art)
            MovieImageTypeImp.PROMO -> context.getString(R.string.promo)
            MovieImageTypeImp.CONCEPT -> context.getString(R.string.concept)
            MovieImageTypeImp.WALLPAPER -> context.getString(R.string.wallpaper)
            MovieImageTypeImp.COVER -> context.getString(R.string.cover)
            MovieImageTypeImp.SCREENSHOT -> context.getString(R.string.screenshot)
            else -> context.getString(R.string.unknown)
        }
    }
}

