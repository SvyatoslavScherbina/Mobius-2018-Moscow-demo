package sample.model

class WikiRepositoryImpl : WikiRepository {
    private val api = WikiApi()

    private var cachedLocation: Location? = null
    private var cachedResults: List<WikiPage> = emptyList()

    override suspend fun getNearestPages(location: Location): List<WikiPage> {
        cachedLocation?.let { cachedLocation ->
            if (location.distanceTo(cachedLocation) < 10.0) return cachedResults
        }

        return api.getNearestPages(location).also {
            cachedLocation = location
            cachedResults = it
        }
    }
}