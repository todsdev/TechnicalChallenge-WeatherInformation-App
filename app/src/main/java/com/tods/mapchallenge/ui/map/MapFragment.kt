package com.tods.mapchallenge.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tods.mapchallenge.R
import com.tods.mapchallenge.data.model.CoordModel
import com.tods.mapchallenge.databinding.FragmentMapBinding
import com.tods.mapchallenge.state.ResourceState
import com.tods.mapchallenge.ui.base.BaseFragment
import com.tods.mapchallenge.ui.information.InformationFragmentArgs
import com.tods.mapchallenge.ui.information.InformationViewModel
import com.tods.mapchallenge.ui.location.LocationViewModel
import com.tods.mapchallenge.util.hide
import com.tods.mapchallenge.util.show
import com.tods.mapchallenge.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MapFragment: BaseFragment<FragmentMapBinding, MapViewModel>(), OnMapReadyCallback {
    override val viewModel: MapViewModel by viewModels()
    override fun recoverViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)
    private lateinit var mMap: GoogleMap
    private lateinit var model: CoordModel
    private val args: MapFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMapFragment()
        configInitialSettings()
        configCollectObserver()
    }

    private fun configCollectObserver() = lifecycleScope.launch {
        viewModel.map.collect { result ->
            when (result) {
                is ResourceState.Success -> {
                    binding.progressBarMap.hide()
                    result.data?.let { values ->
                        createMapMark(values.coord.lat, values.coord.lon)
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBarMap.show()
                }

                is ResourceState.Error -> {
                    binding.progressBarMap.hide()
                    result.message?.let { message ->
                        toast(getString(R.string.failed))
                        Timber.tag("MapFragment").e(message)
                    }
                }

                else -> { }
            }
        }
    }

    private fun createMapMark(lat: Float, lon: Float) {
        val location = LatLng(lat.toDouble(), lon.toDouble())
        mMap.addMarker(MarkerOptions().position(location).title("Location Mark"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13.5f))
    }

    private fun configInitialSettings() {
        model = args.latLon
        viewModel.fetch(model.lat, model.lon)
    }

    private fun configMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}