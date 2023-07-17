package com.levendoglu.weatherappretrofit.API

import com.levendoglu.weatherappretrofit.model.WeatherModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    fun getCurrentWeatherData(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("APPID") appId: String,
        @Query("units") units:String,
    ) : Call<WeatherModel>

}