package com.tods.mapchallenge.repository

import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.tods.mapchallenge.data.remote.ServiceAPI
import com.tods.mapchallenge.util.Constants
import timber.log.Timber
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: ServiceAPI
    ) {

    suspend fun getWeatherInformation(lat: Float, lon: Float) = api.getWeatherInformation(lat, lon, Constants.API_KEY)
}