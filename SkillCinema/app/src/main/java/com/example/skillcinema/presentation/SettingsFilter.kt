package com.example.skillcinema.presentation

import com.example.skillcinema.R
import com.example.skillcinema.entity.Country
import com.example.skillcinema.entity.Genre
import com.example.skillcinema.entity.MovieFilter

sealed class SettingsFilter(val filterName: Int, val hint: Int, val filterList: List<MovieFilter>) {
    class COUNTRY(filterList: List<Country>) : SettingsFilter(
        filterName = R.string.country,
        hint = R.string.enter_country,
        filterList = filterList
    )

    class GENRE(filterList: List<Genre>) : SettingsFilter(
        filterName = R.string.genre,
        hint = R.string.enter_genre,
        filterList = filterList
    )
}