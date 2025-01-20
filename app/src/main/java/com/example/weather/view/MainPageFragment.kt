package com.example.weather.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.adapter.RecylerViewAdapter
import com.example.weather.databinding.FragmentMainPageBinding
import com.example.weather.models.CityWeather
import com.example.weather.models.WeatherResponse
import com.example.weather.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPageFragment : Fragment() {
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    private val weatherList = mutableListOf<CityWeather>()
    private lateinit var weatherAdapter: RecylerViewAdapter
    private val apiKey by lazy { getString(R.string.weather_api_key) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView kurulum
        weatherAdapter = RecylerViewAdapter(weatherList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherAdapter
        }

        // Spinner için veri ekleme ve adapter kurulum
        val cities = resources.getStringArray(R.array.city_list)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cities)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.citySpinner.adapter = spinnerAdapter

        // Ekle butonu işlevi
        binding.addButton.setOnClickListener {
            val selectedCity = binding.citySpinner.selectedItem.toString()
            fetchWeatherForCity(selectedCity)
        }
    }

    private fun fetchWeatherForCity(city: String) {
        // Retrofit ile API çağrısı yapalım
        RetrofitInstance.api.getCurrentWeather(apiKey, city).enqueue(object :
            Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        // Veriyi aldıktan sonra listeye ekleyip adapter'ı güncelle
                        val cityWeather = CityWeather(city, weatherResponse.current.temperature,
                            weatherResponse.current.weather_icons.first(),
                            weatherResponse.current.weather_icons.first())
                        weatherList.add(cityWeather)
                        weatherAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Hata durumunda yapılacak işlemler
                Log.e("WeatherError", "API Hatası: ${t.message}")
                Toast.makeText(requireContext(), "Hata Alınıyor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
