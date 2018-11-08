package sample.model

interface LocationService {
    val onLocationUpdateListeners: MutableList<() -> Unit>
    val location: Location?
    fun start()
    fun stop()
}

abstract class AbstractLocationService : LocationService {
    override val onLocationUpdateListeners = mutableListOf<() -> Unit>()

    final override var location: Location? = null
        private set

    private fun notifyListeners() {
        onLocationUpdateListeners.forEach { it() }
    }

    protected fun update(location: Location) {
        this.location = location
        notifyListeners()
    }
}