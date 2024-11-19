package com.example.jetweatherapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val _temperatureUnit = MutableStateFlow("Celsius (°C)")
    val temperatureUnit: StateFlow<String> = _temperatureUnit.asStateFlow()

    suspend fun getFromSharedPreferences(key: String) {
        viewModelScope.launch {
            val sharedPref = context.getSharedPreferences("setting_pref", Context.MODE_PRIVATE)
            val value = sharedPref.getString(key, "")
            if (value != null) {
                _temperatureUnit.emit(value)
            }
        }
    }

    fun saveToSharedPreferences(key: String, value: String) {
        val sharedPref = context.getSharedPreferences("setting_pref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }
}