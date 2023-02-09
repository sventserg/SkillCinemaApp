package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.dto.MovieListDto
import com.example.skillcinema.data.dto.SeasonsDto
import com.example.skillcinema.data.retrofit.LoadMovieListRetrofit
import com.example.skillcinema.data.retrofit.LoadSeasonsRetrofit
import com.example.skillcinema.entity.MovieList
import com.example.skillcinema.entity.Season
import com.example.skillcinema.entity.Seasons
import com.example.skillcinema.entity.data.repository.LoadSeasonsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadSeasonsRepositoryImp : LoadSeasonsRepository {

    private val seasons = MutableStateFlow<Seasons?>(null)
    private var seasonsIsLoaded = false

    private fun seasonsResponse(id: Int) {
        LoadSeasonsRetrofit.loadSeasonsApi.loadSeasons(id).enqueue(object : Callback<SeasonsDto> {
            override fun onResponse(call: Call<SeasonsDto>, response: Response<SeasonsDto>) {
                val responseCode = response.code()
                val responseBody = response.body()
                seasons.value = responseBody
                Log.d(
                    RETROFIT_TAG,
                    "Response code, load seasons: $responseCode"
                )
                seasonsIsLoaded = true
            }

            override fun onFailure(call: Call<SeasonsDto>, t: Throwable) {
                Log.d(RETROFIT_TAG, "${t.message}")
                seasons.value = null
                seasonsIsLoaded = true
            }
        })
    }

    override suspend fun loadSeasons(id: Int): Seasons? {
        seasonsResponse(id)
        while (!seasonsIsLoaded) {
            delay(100)
        }
        seasonsIsLoaded = false
        return seasons.value
    }

    companion object {
        private const val RETROFIT_TAG = "RETROFIT"
    }
}