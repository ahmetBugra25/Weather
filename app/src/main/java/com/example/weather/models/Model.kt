package com.example.weather.models

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String
)

data class Current(
    val temperature: Int,
    val weather_icons: List<String>
)