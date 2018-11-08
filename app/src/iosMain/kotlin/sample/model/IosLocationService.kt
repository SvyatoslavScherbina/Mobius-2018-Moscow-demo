package sample.model

import kotlinx.cinterop.useContents
import platform.Contacts.*
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun CLLocation.toLocation() = this.coordinate().useContents { Location(latitude, longitude) }

class LocationServiceImpl : AbstractLocationService() {

    private val manager = CLLocationManager()

    private val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            update((didUpdateLocations.last() as CLLocation).toLocation())
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            // Ignore for now.
        }
    }

    override fun start() {
        manager.delegate = delegate
        manager.requestAlwaysAuthorization()
        manager.startUpdatingLocation()
    }

    override fun stop() {
        manager.stopUpdatingLocation()
    }
}