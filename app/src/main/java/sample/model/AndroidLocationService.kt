package sample.model

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import sample.presenter.PermissionManager

class LocationServiceImpl(private val application: Application): AbstractLocationService() {

    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    suspend fun requestPermissions(permissionManager: PermissionManager) {
        permissionManager.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun start() {
        if (application.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return // Ignore for now.
        }

        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
            update(it.toLocation())
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
    }

    override fun stop() {
        locationManager.removeUpdates(listener)
    }

    private val listener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: android.location.Location?) {
            location ?: return
            update(location.toLocation())
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String?) {}
        override fun onProviderDisabled(provider: String?) {}
    }

}

private fun android.location.Location.toLocation() = Location(latitude, longitude)
