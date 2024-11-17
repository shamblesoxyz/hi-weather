package com.example.hiweather.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private lateinit var locationCallback: LocationCallback
    private val _currentLocationFlow = MutableStateFlow<Location?>(null)
    val currentLocationFlow = _currentLocationFlow.asStateFlow()

    fun startLocationUpdates() {
        viewModelScope.launch {
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 60000L)
                .setMinUpdateIntervalMillis(60000L) // Set the fastest update interval
                .setWaitForAccurateLocation(true)   // Optional: Wait for accurate location
                .build()
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    viewModelScope.launch {
                        _currentLocationFlow.emit(p0.lastLocation)
                    }
                }
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            }
        }
    }

    fun stopLocationUpdates() {
        if (::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}