package sample.model

import platform.CoreLocation.CLLocation

private fun Location.toCLLocation() = CLLocation(this.latitude, this.longitude)

actual fun Location.distanceTo(other: Location): Double {
    val clThis = this.toCLLocation()
    val clOther = other.toCLLocation()

    return clThis.distanceFromLocation(clOther)
}