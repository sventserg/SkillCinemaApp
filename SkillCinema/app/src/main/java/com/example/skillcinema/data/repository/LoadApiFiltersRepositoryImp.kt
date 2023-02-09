package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.dto.ApiFiltersDto
import com.example.skillcinema.data.retrofit.LoadApiFiltersRetrofit
import com.example.skillcinema.entity.ApiFilters
import com.example.skillcinema.entity.data.repository.LoadApiFiltersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadApiFiltersRepositoryImp : LoadApiFiltersRepository {

    private val apiFilters = MutableStateFlow<ApiFilters?>(null)
    private var apiFiltersIsLoaded = false

    private fun apiFiltersResponse() {
        LoadApiFiltersRetrofit.loadApiFiltersApi.loadApiFilters()
            .enqueue(object : Callback<ApiFiltersDto> {
                override fun onResponse(
                    call: Call<ApiFiltersDto>,
                    response: Response<ApiFiltersDto>
                ) {
                    val responseCode = response.code()
                    val responseBody = response.body()
                    apiFilters.value = responseBody
                    Log.d(RETROFIT_TAG, "Response code, load api filters: $responseCode")
                    apiFiltersIsLoaded = true
                }

                override fun onFailure(call: Call<ApiFiltersDto>, t: Throwable) {
                    Log.e(RETROFIT_TAG, "${t.message}")
                    apiFilters.value = null
                    apiFiltersIsLoaded = true
                }
            })
    }

    override suspend fun loadInfo(): ApiFilters? {
        apiFiltersResponse()
        while (!apiFiltersIsLoaded) {
            delay(100)
        }
        apiFiltersIsLoaded = false
        return apiFilters.value
    }

    companion object {
        private const val RETROFIT_TAG = "RETROFIT"
    }
}