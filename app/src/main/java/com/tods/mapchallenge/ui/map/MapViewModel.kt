package com.tods.mapchallenge.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tods.mapchallenge.data.model.CoordModel
import com.tods.mapchallenge.data.model.ResponseModel
import com.tods.mapchallenge.repository.WeatherRepository
import com.tods.mapchallenge.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    private val _map = MutableStateFlow<ResourceState<ResponseModel>>(ResourceState.Loading())
    var map: StateFlow<ResourceState<ResponseModel>> = _map

    fun fetch(lat: Float, lon: Float) = viewModelScope.launch {
        safeFetch(lat, lon)
    }

    private suspend fun safeFetch(lat: Float, lon: Float) {
        _map.value = ResourceState.Loading()
        try {
            val response = repository.getWeatherInformation(lat, lon)
            _map.value = handleResponse(response)
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _map.value = ResourceState.Error("An error with internet connection happened")
                else -> _map.value = ResourceState.Error("An error converting data occurred")
            }
        }
    }

    private fun handleResponse(response: Response<ResponseModel>): ResourceState<ResponseModel> {
        if (response.isSuccessful) {
            response.body().let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}