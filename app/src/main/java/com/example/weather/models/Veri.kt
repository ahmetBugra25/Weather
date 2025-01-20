package com.example.weather.models

data class CityWeather(
    val cityName: String,
    val temperature: Int,
    val iconUrl: String,
    val s: String
)