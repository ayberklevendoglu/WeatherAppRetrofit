package com.levendoglu.weatherappretrofit.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.levendoglu.weatherappretrofit.API.WeatherAPI
import com.levendoglu.weatherappretrofit.API.WeatherApiService
import com.levendoglu.weatherappretrofit.R
import com.levendoglu.weatherappretrofit.databinding.ActivityMainBinding
import com.levendoglu.weatherappretrofit.model.WeatherModel
import com.levendoglu.weatherappretrofit.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var apiKey = "b43b053e60ac3e5a9562454d998050aa"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrentWeather()
        binding.imgLocation.setOnClickListener {
            startActivity(Intent(this@MainActivity,MapsActivity::class.java))
        }
    }

    private fun getCurrentWeather() {
        val latitude = intent.getDoubleExtra("Latitude",39.925893228300986)
        val longitude = intent.getDoubleExtra("Longitude",32.83707264602226)
        WeatherApiService.api.getCurrentWeatherData(latitude,longitude, apiKey,"metric").enqueue(object : Callback<WeatherModel>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful && response.body() != null){
                    response.body()?.let {
                        val data = response.body()!!

                        val iconId = data.weather[0].icon

                        val imgUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"

                        Picasso.get()
                            .load(imgUrl)
                            .resize(100,100)
                            .centerCrop()
                            .into(binding.imgWeather)


                        binding.apply {
                            temp.text = "${ response.body()!!.main.temp.toUInt() }°"
                            updated.text= "Updated at : " + viewModel.dateConverter(data.dt.toLong())
                            location.text = "${data.name}\n${data.sys.country}"
                            maxTemp.text = "Max temp " + data.main.temp_max.toUInt() + "°"
                            minTemp.text = "Min temp " + data.main.temp_min.toUInt() + "°"
                            feelsLike.text = "Feels like " + data.main.feels_like.toUInt() + "°"
                            tvSunrise.text = viewModel.dateConverter(data.sys.sunrise.toLong())
                            tvSunset.text = viewModel.dateConverter(data.sys.sunset.toLong())
                            tvWind.text = "${data.wind.speed} KM/H"
                            tvHumidity.text = "${data.main.humidity} %"
                        }


                        if (data.clouds.all <= 10) binding.status.text = "Clear Sky"
                        if (data.clouds.all in 11..25) binding.status.text = "Few Clouds"
                        if (data.clouds.all in 26..50) binding.status.text = "Scattered Clouds"
                        if (data.clouds.all in 51..84) binding.status.text = "Broken Clouds"
                        if (data.clouds.all in 86..100) binding.status.text = "Overcast Clouds"
                    }
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }
}