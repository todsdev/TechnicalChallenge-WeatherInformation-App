package com.tods.mapchallenge.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CoordModel(
    @SerializedName("lon")
    val lon: Float,
    @SerializedName("lat")
    val lat: Float
): Serializable