package com.tods.mapchallenge.ui.location

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tods.mapchallenge.R
import com.tods.mapchallenge.data.model.CoordModel
import com.tods.mapchallenge.databinding.FragmentLocationBinding
import com.tods.mapchallenge.ui.base.BaseFragment
import com.tods.mapchallenge.util.Constants
import com.tods.mapchallenge.util.Constants.DEFAULT_QUERY
import com.tods.mapchallenge.util.Constants.LAST_SEARCH_QUERY
import com.tods.mapchallenge.util.toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException

@AndroidEntryPoint
class LocationFragment: BaseFragment<FragmentLocationBinding, LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()
    override fun recoverViewBinding(inflater: LayoutInflater, container: ViewGroup?):
            FragmentLocationBinding = FragmentLocationBinding.inflate(inflater, container, false)
    private lateinit var fusedLocation: FusedLocationProviderClient
    private lateinit var sharedPreferences: SharedPreferences
    private var permission: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
        if (isGranted){
            Log.i("PERMISSION", "onCreate: granted")
        } else {
            Log.i("PERMISSION", "onCreate: denied")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configQuerySearch(savedInstanceState)
        configButtonSearchClickListener()
        configRequestPermission()
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)?: return
        val value = sharedPreferences.getInt(getString(R.string.readed_how_to_use), Constants.DEFAULT_VALUE)
        if (value == Constants.DEFAULT_VALUE) {
            sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)?: return
            with(sharedPreferences.edit()) {
                putInt(getString(R.string.readed_how_to_use), Constants.UPDATED_VALUE)
                apply()
            }
            configOneTimeDialog()
        }
    }

    private fun configOneTimeDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("HOW TO USE")
            .setMessage(
                "There is two different ways to recover the location you want:\n" +
                        "First is use the text address with enter/keyboard function to push the information and \n" +
                        "Second is using the pre selected locations with the 'Search' button"
            )
            .setPositiveButton(getString(R.string.understood)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun configRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED  -> { }
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) -> {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Constants.SELECTION_LOCATION)
            }
            else -> {
                permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_DENIED){
            toast(getString(R.string.in_order_to))
        }
    }

    private fun configButtonSearchClickListener() = with(binding) {
        buttonSearch.setOnClickListener {
            val buttonId = radioGroup.checkedRadioButtonId
            if (buttonId != -1) {
                when(buttonId) {
                    R.id.button_lisbon -> {
                        val recoveredLatLon = recoverLocationFromAddress("lisbon")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_copenhagen -> {
                        val recoveredLatLon = recoverLocationFromAddress("copenhagen")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_dublin -> {
                        val recoveredLatLon = recoverLocationFromAddress("dublin")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_london -> {
                        val recoveredLatLon = recoverLocationFromAddress("london")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_madrid -> {
                        val recoveredLatLon = recoverLocationFromAddress("madrid")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_paris -> {
                        val recoveredLatLon = recoverLocationFromAddress("paris")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_prague -> {
                        val recoveredLatLon = recoverLocationFromAddress("prague")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_rome -> {
                        val recoveredLatLon = recoverLocationFromAddress("rome")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_vienna -> {
                        val recoveredLatLon = recoverLocationFromAddress("vienna")
                        radioButtonFunction(recoveredLatLon)
                    }
                    R.id.button_current -> {
                        var latLon: CoordModel?
                        checkPermission()
                        fusedLocation.lastLocation.addOnSuccessListener { location ->
                           if(location != null) {
                               latLon = CoordModel(location.longitude.toFloat(), location.latitude.toFloat())
                               radioButtonFunction(latLon)
                           } else {
                               toast(getString(R.string.failed_to_recover_location))
                           }
                        }
                    }
                }
            } else {
                toast(getString(R.string.select_first))
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            toast(getString(R.string.in_order_to))
        }
    }

    private fun radioButtonFunction(recoveredLatLon: CoordModel?) {
        searchQuery(recoveredLatLon!!.lat, recoveredLatLon.lon)
        val action = LocationFragmentDirections.actionLocationFragmentToInformationFragment(recoveredLatLon)
        findNavController().navigate(action)
    }

    private fun recoverLocationFromAddress(address: String): CoordModel? {
        val geocoder: Geocoder = Geocoder(requireContext())
        val addressList: List<Address>
        var latLon: CoordModel? = null
        try {
            addressList = geocoder.getFromLocationName(address, 5)
            latLon = if (addressList.isEmpty()) {
                Constants.DEFAULT_LATLONG
            } else {
                val location: Address = addressList[0]
                CoordModel(location.longitude.toFloat(), location.latitude.toFloat())
            }
        } catch (e: IOException) {
            Timber.tag("RecoverLocation").e(e)
        }
        return latLon
    }

    private fun configQuerySearch(savedInstanceState: Bundle?) {
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY)?: DEFAULT_QUERY
        searchInit(query)
    }

    private fun searchInit(query: String) = with(binding) {
        editSearch.setText(query)
        editSearch.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_GO) {
                updateView()
                true
            } else {
                false
            }
        }
        editSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if(keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateView()
                true
            } else {
                false
            }
        }
    }

    private fun updateView() = with(binding) {
        editSearch.editableText.toString().let { query ->
            if (query.isNotEmpty()) {
                val latLon = recoverLocationFromAddress(query)
                radioButtonFunction(latLon)
            }
        }
    }

    private fun searchQuery(latitude: Float, longitude: Float) {
        viewModel.fetch(latitude, longitude)
    }
}