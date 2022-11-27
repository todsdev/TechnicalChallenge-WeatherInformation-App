package com.tods.mapchallenge.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tods.mapchallenge.data.model.ResponseModel
import com.tods.mapchallenge.repository.WeatherRepository
import com.tods.mapchallenge.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    private val _search = MutableStateFlow<ResourceState<ResponseModel>>(ResourceState.Empty())

    fun fetch(latitude: Float, longitude: Float) = viewModelScope.launch {
        safeFetch(latitude, longitude)
    }

    private suspend fun safeFetch(latitude: Float, longitude: Float) {
        _search.value = ResourceState.Loading()
        try {
            val response = repository.getWeatherInformation(latitude, longitude)
            _search.value = handleResponse(response)
        } catch (t: Throwable) {
            when(t) {
                is IOException -> _search.value = ResourceState.Error("An error with internet connection occurred")
                else -> _search.value = ResourceState.Error("An error converting data occurred")
            }
        }
    }

    private fun handleResponse(response: Response<ResponseModel>): ResourceState<ResponseModel> {
        if (response.isSuccessful) {
            response.body().let { values ->
                return ResourceState.Success(values)
            }
        } else {
            return ResourceState.Error(response.message())
        }
    }
}