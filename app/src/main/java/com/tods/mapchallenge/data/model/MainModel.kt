package com.tods.mapchallenge.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MainModel(
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int
): Serializable
