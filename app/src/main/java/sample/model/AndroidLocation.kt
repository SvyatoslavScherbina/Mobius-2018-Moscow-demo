package sample.model

private fun Location.toAndroidLocation() = android.location.Location("").also {
    it.latitude = latitude
    it.longitude = longitude
}

actual fun Location.distanceTo(other: Location): Double {
    return this.toAndroidLocation().distanceTo(other.toAndroidLocation()).toDouble()
}