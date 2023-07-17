package com.levendoglu.weatherappretrofit.API

import com.levendoglu.weatherappretrofit.model.WeatherModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object WeatherApiService {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)
}