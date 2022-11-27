package com.tods.mapchallenge.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tods.mapchallenge.R
import com.tods.mapchallenge.databinding.FragmentMapBinding
import com.tods.mapchallenge.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: BaseFragment<FragmentMapBinding, MapViewModel>(), OnMapReadyCallback {
    override val viewModel: MapViewModel by viewModels()
    override fun recoverViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)
    private lateinit var mMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMapFragment()
    }

    private fun configMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val rj = LatLng(-22.911031, -43.229413)
        mMap.addMarker(MarkerOptions().position(rj).title("Marker in Rio de Janeiro"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rj))
    }
}