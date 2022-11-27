package com.tods.mapchallenge.ui.information

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tods.mapchallenge.R
import com.tods.mapchallenge.data.model.CoordModel
import com.tods.mapchallenge.data.model.ResponseModel
import com.tods.mapchallenge.data.model.WeatherModel
import com.tods.mapchallenge.databinding.FragmentInformationBinding
import com.tods.mapchallenge.state.ResourceState
import com.tods.mapchallenge.ui.base.BaseFragment
import com.tods.mapchallenge.util.hide
import com.tods.mapchallenge.util.limitedDescription
import com.tods.mapchallenge.util.show
import com.tods.mapchallenge.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class InformationFragment: BaseFragment<FragmentInformationBinding, InformationViewModel>() {
    override val viewModel: InformationViewModel by viewModels()
    override fun recoverViewBinding(inflater: LayoutInflater, container: ViewGroup?):
            FragmentInformationBinding = FragmentInformationBinding.inflate(inflater, container, false)
    private val args: InformationFragmentArgs by navArgs()
    private lateinit var model: CoordModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configInitialSettings()
        configCollectObserver()
    }

    private fun configInitialSettings() {
        model = args.latLon
        viewModel.fetch(model.lat, model.lon)
    }

    private fun configCollectObserver() = lifecycleScope.launch {
        viewModel.details.collect {  result ->
             when(result) {
                is ResourceState.Success -> {
                    binding.progressBarInformation.hide()
                    result.data?.let { values ->
                        binding.textLatitude.text = values.coord.lat.toString().limitedDescription(6)
                        binding.textLongitude.text = values.coord.lon.toString().limitedDescription(6)
                        binding.textSunrise.text = values.sys.sunrise.toString().limitedDescription(6)
                        binding.textSunset.text = values.sys.sunset.toString().limitedDescription(6)
                        binding.textCountry.text = values.sys.country
                        binding.textTemperature.text = values.main.temp.toString().limitedDescription(6)
                        binding.textHumidity.text = values.main.humidity.toString().limitedDescription(6)
                        binding.textPressure.text = values.main.pressure.toString().limitedDescription(6)
                        binding.textVisibility.text = values.visibility.toString().limitedDescription(6)
                        binding.textWindSpeed.text = values.wind.speed.toString().limitedDescription(6)
                        binding.textTimezone.text = values.timezone.toString()
                        binding.textDescription.text = values.weather[0].description
                        binding.textResume.text = values.weather[0].main
                    }
                }
                is ResourceState.Error -> {
                    binding.progressBarInformation.hide()
                    result.message?.let { message ->
                        toast(getString(R.string.failed))
                        Timber.tag("InformationFragment").e(message)
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressBarInformation.show()
                }
                else -> { }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.openMapFragment -> {
                val action = InformationFragmentDirections.actionInformationFragmentToMapFragment(model)
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}