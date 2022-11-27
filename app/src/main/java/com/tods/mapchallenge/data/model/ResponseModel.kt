package com.tods.mapchallenge.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseModel(
    @SerializedName("coord")
    val coord: CoordModel,
    @SerializedName("weather")
    val weather: List<WeatherModel>,
    @SerializedName("main")
    val main: MainModel,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind: WindModel,
    @SerializedName("sys")
    val sys: SysModel,
    @SerializedName("timezone")
    val timezone: Int
): Serializable
