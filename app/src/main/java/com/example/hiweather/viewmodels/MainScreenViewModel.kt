package com.example.jetweatherapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherapp.data.DataOrException
import com.example.jetweatherapp.model.CurrentWeather
import com.example.jetweatherapp.model.LocationDataItem
import com.example.jetweatherapp.model.WeatherData
import com.example.jetweatherapp.repository.GeocodingRepository
import com.example.jetweatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: WeatherRepository,
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {

    private val _currentWeather =
        MutableStateFlow(DataOrException<CurrentWeather, Boolean, Exception>())
    val currentWeather: StateFlow<DataOrException<CurrentWeather, Boolean, Exception>> =
        _currentWeather.asStateFlow()

    private val _forecast = MutableStateFlow(DataOrException<WeatherData, Boolean, Exception>())
    val forecast: StateFlow<DataOrException<WeatherData, Boolean, Exception>> =
        _forecast.asStateFlow()

    private val _coordinates =
        MutableStateFlow(DataOrException<ArrayList<LocationDataItem>, Boolean, Exception>())
    val coordinates: StateFlow<DataOrException<ArrayList<LocationDataItem>, Boolean, Exception>> =
        _coordinates.asStateFlow()

    private val _temperatureUnit = MutableStateFlow("metric")
    val temperatureUnit: StateFlow<String> = _temperatureUnit.asStateFlow()

    fun getCurrentWeather(latitude: Double, longitude: Double, temperatureUnit: String) {
        viewModelScope.launch {
            _currentWeather.emit(DataOrException(loading = true))
            val result = repository.getCurrentWeather(latitude, longitude, temperatureUnit)
            _currentWeather.emit(result)
            if (result.data != null) {
                _currentWeather.emit(result.copy(loading = false))
            }
        }
    }

    fun get5Day3HourWeatherForecast(latitude: Double, longitude: Double, temperatureUnit: String) {
        viewModelScope.launch {
            _forecast.emit(DataOrException(loading = true))
            val result =
                repository.get5Day3HourWeatherForecast(latitude, longitude, temperatureUnit)
            _forecast.emit(result)
            if (result.data != null) {
                _forecast.emit(result.copy(loading = false))
            }
        }
    }

    fun getCoordinatesByLocationName(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) return@launch
            _coordinates.emit(DataOrException(loading = true))
            val result = geocodingRepository.getCoordinatesByLocationName(query)
            _coordinates.emit(result)
            if (result.data != null && result.data!!.isNotEmpty()) {
                _coordinates.emit(result.copy(loading = false))
            }
        }
    }

    fun getCoordinatesByZipCode(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) return@launch
            _coordinates.emit(DataOrException(loading = true))
            val result = geocodingRepository.getCoordinatesByZipCode(query)
            _coordinates.emit(result)
            if (result.data != null && result.data!!.isNotEmpty()) {
                _coordinates.emit(result.copy(loading = false))
            }
        }
    }

    suspend fun getTemperatureUnit() {
        viewModelScope.launch {
            val sharedPref = context.getSharedPreferences("setting_pref", Context.MODE_PRIVATE)
            val value = sharedPref.getString("temp_unit", "metric")
            if (value != null) {
                _temperatureUnit.emit(value)
            }
        }
    }

}