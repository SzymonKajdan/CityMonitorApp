package com.inz.citymonitor.presentation.pages.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.inz.citymonitor.R
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : BaseFragment(), OnMapReadyCallback {
    override fun setTopBarTitle(): String? {
        return "City Monitor"
    }

    private var map: GoogleMap? = null
    private var lastKnownLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel by lazy { MapViewModel() }
    private var locationRequest: LocationRequest? = null
    private lateinit var locationCallback: LocationCallback
    private var requestingLocationUpdates: Boolean = false

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.apply {
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.801556, 19.456455), 8f))
            uiSettings.isMapToolbarEnabled = false

            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                isMyLocationEnabled = true
            } else {
                sendRequest(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        setMapListeners()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updatesValuesFromBundle(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
        activity?.let {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(it.applicationContext)
        }

        checkLocationSettings()
        currentLocation.setOnClickListener {
            lastKnownLocation?.let {
                moveCamera(LatLng(it.latitude, it.longitude), 9f)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (requestingLocationUpdates) startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        if (requestingLocationUpdates) stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun startLocationUpdates() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS: Int = 81
        const val LOCATION_REQUEST_CODE: Int = 101
        const val REQUESTING_LOCATION_UPDATES_KEY: String = "REQUEST_LOCATION_UPDATES"
    }

    private fun checkLocationSettings() {
        createLocationRequest()
        locationRequest?.let {
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(it)
            val client: SettingsClient = LocationServices.getSettingsClient(activity ?: return)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.apply {
                addOnSuccessListener {
                    requestingLocationUpdates = true
                    defineLocationCallback()
                    startLocationUpdates()
                }
                addOnFailureListener {
                    requestingLocationUpdates = false
                    if (exception is ResolvableApiException) {
                        try {
                            startIntentSenderForResult(
                                (exception as ResolvableApiException).resolution.intentSender,
                                REQUEST_CHECK_SETTINGS,
                                null,
                                0,
                                0,
                                0,
                                null
                            )
                        } catch (sendEx: IntentSender.SendIntentException) {
                            // Ignore the error.
                        }
                    }
                }
            }
        }
    }

    private fun defineLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    lastKnownLocation = location
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()?.apply {
            interval = 60000
            fastestInterval = 30000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng, zoom
            )
        )
    }


    private fun setMapListeners() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                map?.isMyLocationEnabled = true
            }
        } else {
            // Permission was denied. Display an error message.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CHECK_SETTINGS -> {
                    requestingLocationUpdates = true
                    defineLocationCallback()
                    startLocationUpdates()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
        super.onSaveInstanceState(outState)
    }

    private fun updatesValuesFromBundle(savedInstanceState: Bundle?) {
        savedInstanceState ?: return
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            requestingLocationUpdates =
                savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY)
        }
    }

    fun sendRequest(permission: String) {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(permission),
                        LOCATION_REQUEST_CODE
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(permission),
                        LOCATION_REQUEST_CODE
                    )
                }
            }
        }

    }

    fun checkPermission(permission: String): Boolean {

        return (ContextCompat.checkSelfPermission(activity?.applicationContext ?: return false, permission)
                == PackageManager.PERMISSION_GRANTED)
    }


}