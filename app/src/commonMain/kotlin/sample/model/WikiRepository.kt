package sample.model

interface WikiRepository {
    suspend fun getNearestPages(location: Location): List<WikiPage>
}