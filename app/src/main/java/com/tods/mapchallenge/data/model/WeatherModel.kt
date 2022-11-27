package com.tods.mapchallenge.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherModel(
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String
): Serializable
