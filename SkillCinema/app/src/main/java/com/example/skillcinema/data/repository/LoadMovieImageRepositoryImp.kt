package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.dto.MovieImageListDto
import com.example.skillcinema.data.retrofit.LoadMovieDataRetrofit
import com.example.skillcinema.entity.MovieImageList
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.entity.repository.LoadMovieImageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadMovieImageRepositoryImp : LoadMovieImageRepository {

    private val movieImageList = MutableStateFlow<MovieImageList?>(null)
    private var movieImageListIsLoaded = false

    private fun movieImageResponse(
        kinopoiskId: Int,
        page: Int,
        type: MovieImageType
    ) {
        LoadMovieDataRetrofit.loadMovieDataApi.loadMovieImages(
            kinopoiskId = kinopoiskId, page = page, type = type.type
        ).enqueue(object : Callback<MovieImageListDto> {
            override fun onResponse(
                call: Call<MovieImageListDto>,
                response: Response<MovieImageListDto>
            ) {
                val responseCode = response.code()
                val responseBody = response.body()
                movieImageList.value = responseBody
                Log.d(RETROFIT_TAG, "Response code, load movie images: $responseCode")
                movieImageListIsLoaded = true
            }

            override fun onFailure(call: Call<MovieImageListDto>, t: Throwable) {
                Log.e(RETROFIT_TAG, "${t.message}")
                movieImageList.value = null
                movieImageListIsLoaded = true
            }

        })
    }

    override suspend fun loadTypedImages(
        kinopoiskId: Int,
        page: Int,
        type: MovieImageType
    ): MovieImageList? {
        movieImageResponse(kinopoiskId, page, type)
        while (!movieImageListIsLoaded) {
            delay(100)
        }
        movieImageListIsLoaded = false
        return movieImageList.value
    }

    companion object {
        private const val RETROFIT_TAG = "RETROFIT"
    }
}