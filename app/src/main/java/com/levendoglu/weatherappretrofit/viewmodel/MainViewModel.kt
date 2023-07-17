package com.levendoglu.weatherappretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.levendoglu.weatherappretrofit.API.WeatherApiService
import com.levendoglu.weatherappretrofit.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel(){

    fun dateConverter(date: Long): String {

        return SimpleDateFormat(
            "hh:mm a",
            Locale.ENGLISH
        ).format(Date(date * 1000))
    }

}