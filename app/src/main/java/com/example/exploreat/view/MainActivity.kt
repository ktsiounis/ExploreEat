package com.example.exploreat.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exploreat.R
import com.example.exploreat.adapter.PlacesRVAdapter
import com.example.exploreat.data.model.DomainPlaceModel
import com.example.exploreat.data.model.Result
import com.example.exploreat.databinding.ActivityMainBinding
import com.example.exploreat.network.APIClient
import com.example.exploreat.repository.MainRepository
import com.example.exploreat.utils.Constants
import com.example.exploreat.utils.Utils
import com.example.exploreat.viewmodel.MainActivityViewModel
import com.example.exploreat.viewmodel.MainActivityViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener, PlacesRVAdapter.PlaceClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private val fusedLocationClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val placesRVAdapter: PlacesRVAdapter by lazy { PlacesRVAdapter(arrayListOf(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()

        initViews()

    }

    private fun initViewModel() {
        val service = APIClient.getClientInstance()
        val mainRepository = MainRepository(service = service)

        viewModel = ViewModelProvider(this, MainActivityViewModelFactory(mainRepository)).get(MainActivityViewModel::class.java)

        viewModel.placesLiveData.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        if (it.isNotEmpty()) {
                            setPlacesRecyclerViewVisibility(true)
                            placesRVAdapter.replaceItems(ArrayList(it))
                        } else {
                            setPlacesRecyclerViewVisibility(false)
                            showNoResultsToast()
                        }
                    } ?: run {
                        setPlacesRecyclerViewVisibility(false)
                        showNoResultsToast()
                    }
                }
                is Result.Error -> {
                    setPlacesRecyclerViewVisibility(false)
                    showErrorDialog(result.message ?: getString(R.string.default_error_message))
                }
                is Result.Loading -> {
                    //show loading
                }
            }
        }
    }

    private fun showNoResultsToast() = Toast.makeText(
        this,
        getString(R.string.no_results_toast_message),
        LENGTH_LONG
    ).show()

    private fun initViews() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.placesRecyclerView.apply {
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = placesRVAdapter
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLastKnownLocation()
    }

    override fun onCameraIdle() {
        val midLatLng = mMap.cameraPosition.target
        viewModel.searchForPlaces(midLatLng)
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        if (!Utils.hasLocationPermissions(this)) {
            requestLocationPermissions()
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    mMap.apply {
                        moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                        setOnCameraIdleListener(this@MainActivity)
                    }
                }

            }

    }

    private fun requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Constants.LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Utils.hasLocationPermissions(this)) {
                        getLastKnownLocation()
                    }
                } else {
                    showPermissionRejectedDialog()
                }
            }
        }
    }

    private fun showPermissionRejectedDialog() {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.permission_alert_title))
            .setMessage(getString(R.string.permission_alert_message))
            .setPositiveButton(getString(R.string.permission_alert_btn_text)) { dialogInterface, i -> dialogInterface.dismiss() }
            .show()
    }

    private fun showErrorDialog(message:String) {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
            .setMessage(message)
            .setPositiveButton(getString(R.string.error_dialog_btn_text)) { dialogInterface, i -> dialogInterface.dismiss() }
            .show()
    }

    override fun onPlaceClicked(place: DomainPlaceModel) {
        TODO("Not yet implemented")
    }

    private fun setPlacesRecyclerViewVisibility(visible: Boolean) {
        binding.placesRecyclerView.visibility = if (visible) View.VISIBLE else View.GONE
    }

}