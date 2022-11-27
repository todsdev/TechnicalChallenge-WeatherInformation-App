package com.tods.mapchallenge.ui.map

import androidx.lifecycle.ViewModel
import com.tods.mapchallenge.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    val repository: WeatherRepository
): ViewModel() {

}