package com.tods.mapchallenge.ui.information

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tods.mapchallenge.data.model.ResponseModel
import com.tods.mapchallenge.repository.WeatherRepository
import com.tods.mapchallenge.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    private val _details = MutableStateFlow<ResourceState<ResponseModel>>(ResourceState.Loading())
    var details: StateFlow<ResourceState<ResponseModel>> = _details

    fun fetch(lat: Float, lon: Float) = viewModelScope.launch {
        safeFetch(lat, lon)
    }

    private suspend fun safeFetch(lat: Float, lon: Float) {
        _details.value = ResourceState.Loading()
        try {
            val response = repository.getWeatherInformation(lat, lon)
            _details.value = handleResponse(response)
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _details.value = ResourceState.Error("An error with internet connection happened")
                else -> _details.value = ResourceState.Error("An error converting data occurred")
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