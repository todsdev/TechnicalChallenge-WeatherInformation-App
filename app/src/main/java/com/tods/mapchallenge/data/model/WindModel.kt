package com.tods.mapchallenge.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WindModel(
    @SerializedName("speed")
    val speed: Float
): Serializable
