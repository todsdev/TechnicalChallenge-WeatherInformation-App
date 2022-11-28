package com.tods.mapchallenge.util

import com.google.android.gms.maps.model.LatLng
import com.tods.mapchallenge.data.model.CoordModel

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "0512337fbde5feb77109517c3a2000b5"
    const val LAST_SEARCH_QUERY = "last_search_query"
    const val DEFAULT_QUERY = ""
    const val SELECTION_LOCATION = 100
    const val DEFAULT_VALUE = 100
    const val UPDATED_VALUE = 200
    val DEFAULT_LATLONG: CoordModel = CoordModel(0.000f, 0.000f)

    //https://api.openweathermap.org/data/2.5/weather?lat=38.722252399999995&lon=-9.1393366&appid=0512337fbde5feb77109517c3a2000b5&units=metric
}