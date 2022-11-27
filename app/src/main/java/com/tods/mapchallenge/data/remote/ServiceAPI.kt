package com.tods.mapchallenge.data.remote

import com.tods.mapchallenge.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

    @GET("weather")
    suspend fun getWeatherInformation(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") appId: String
        ): Response<ResponseModel>
}