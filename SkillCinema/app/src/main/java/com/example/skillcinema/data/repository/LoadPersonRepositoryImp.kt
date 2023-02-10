package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.dto.PersonDto
import com.example.skillcinema.data.retrofit.LoadPersonRetrofit
import com.example.skillcinema.entity.Person
import com.example.skillcinema.entity.repository.LoadPersonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadPersonRepositoryImp : LoadPersonRepository {

    private val person = MutableStateFlow<Person?>(null)
    private var personIsLoaded = false

    private fun personResponse(personId: Int) {
        LoadPersonRetrofit.loadPersonApi.loadPersonApi(personId)
            .enqueue(object : Callback<PersonDto> {
                override fun onResponse(call: Call<PersonDto>, response: Response<PersonDto>) {
                    val responseCode = response.code()
                    val responseBody = response.body()
                    person.value = responseBody
                    Log.d(RETROFIT_TAG, "Response code, load person: $responseCode")
                    personIsLoaded = true
                }

                override fun onFailure(call: Call<PersonDto>, t: Throwable) {
                    Log.e(RETROFIT_TAG, "${t.message}")
                    person.value = null
                    personIsLoaded = true
                }
            })
    }

    override suspend fun loadPerson(personId: Int): Person? {
        personResponse(personId)
        while (!personIsLoaded) {
            delay(100)
        }
        personIsLoaded = false
        return person.value
    }

    companion object {
        private const val RETROFIT_TAG = "RETROFIT"
    }
}