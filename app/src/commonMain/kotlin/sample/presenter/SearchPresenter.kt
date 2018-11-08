package sample.presenter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sample.model.*
import sample.view.SearchView

class SearchPresenter(
    private val repository: WikiRepository,
    private val locationService: LocationService,
    val view: SearchView
) {

    private fun onLocationUpdate() {
        present()
    }

    private val onLocationUpdateCallback = ::onLocationUpdate

    fun start() {
        locationService.onLocationUpdateListeners += onLocationUpdateCallback
        locationService.start()
        present()
    }

    fun stop() {
        locationService.stop()
        locationService.onLocationUpdateListeners -= onLocationUpdateCallback
    }

    fun present() {
        locationService.location?.let { location ->
            GlobalScope.launch(coroutineDispatcher) {

                view.results = repository.getNearestPages(location).map {
                    SearchView.ResultItem(
                        it.title,
                        it.dist,
                        "https://en.wikipedia.org/wiki/${it.title}"
                    )
                }.filter {
                    it.matches(view.query)
                }
            }
        }
    }

}

private fun SearchView.ResultItem.matches(query: String): Boolean =
    this.title.contains(query)

expect val coroutineDispatcher: CoroutineDispatcher