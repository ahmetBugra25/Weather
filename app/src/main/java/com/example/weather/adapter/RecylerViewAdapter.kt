package com.example.weather.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.models.CityWeather
import com.squareup.picasso.Picasso

class RecylerViewAdapter(private val weatherList: List<CityWeather>) :
    RecyclerView.Adapter<RecylerViewAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.city_name)
        val temperature: TextView = itemView.findViewById(R.id.temperature)
        val weatherIcon: ImageView = itemView.findViewById(R.id.weather_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.cityName.text = weather.cityName
        holder.temperature.text = "${weather.temperature}°C"
        Picasso.get().load(weather.iconUrl).into(holder.weatherIcon)
    }

    override fun getItemCount() = weatherList.size
}
