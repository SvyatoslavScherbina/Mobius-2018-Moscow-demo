package sample.model

data class Location(val latitude: Double, val longitude: Double)

expect fun Location.distanceTo(other: Location): Double