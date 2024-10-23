package com.example.samojlov_av_homework_module_15_number_7_koala.utils

import com.example.samojlov_av_homework_module_15_number_7_koala.models.ApiData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("woof.json?ref=apilist.fun")
    suspend fun getRandomDog(): Response<ApiData>
}