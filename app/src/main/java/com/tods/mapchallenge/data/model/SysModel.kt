package com.tods.mapchallenge.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SysModel(
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int
): Serializable
