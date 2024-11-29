package com.example.hiweather.repository

import com.example.hiweather.data.DataOrException
import com.example.hiweather.network.GeocodingApi
import com.example.hiweather.model.LocationDataItem
import javax.inject.Inject


class GeocodingRepository @Inject constructor(private val geocodingApi: GeocodingApi) {

    suspend fun getCoordinatesByLocationName(locationName: String): DataOrException<ArrayList<LocationDataItem>, Boolean, Exception> {
        val coordinates = DataOrException<ArrayList<LocationDataItem>, Boolean, Exception>()
        try {
            coordinates.loading = true
            coordinates.data = geocodingApi.getCoordinatesByLocationName(locationName)
        } catch (e: Exception) {
            coordinates.exception = e
        } finally {
            coordinates.loading = false
        }
        return coordinates
    }

    suspend fun getCoordinatesByZipCode(zipCode: String): DataOrException<ArrayList<LocationDataItem>, Boolean, Exception> {
        val coordinates = DataOrException<ArrayList<LocationDataItem>, Boolean, Exception>()
        try {
            coordinates.loading = true
            coordinates.data = geocodingApi.getLocationByZipCode(zipCode)
        } catch (e: Exception) {
            coordinates.exception = e
        } finally {
            coordinates.loading = false
        }
        return coordinates
    }

}