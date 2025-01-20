package com.example.weather.services

import com.example.weather.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current")
    fun getCurrentWeather(
        @Query("access_key") apiKey: String,
        @Query("query") city: String
    ): Call<WeatherResponse>
}