package com.example.skillcinema.domain

import android.content.Context
import com.example.skillcinema.R
import com.example.skillcinema.entity.Seasons

class GetEpisodesNumberUseCase(private val context: Context) {
    fun execute(seasons: Seasons): String {
        val seasonsNumber = seasons.total
        var seriesNumber = 0
        seasons.items.forEach { season ->
            season.episodes.forEach { _ ->
                seriesNumber++
            }
        }
        val partOne =
            context.resources.getQuantityString(R.plurals.series, seasonsNumber, seasonsNumber)
        val partTwo =
            context.resources.getQuantityString(R.plurals.episodes, seriesNumber, seriesNumber)
        return "$partOne $partTwo"
    }
}