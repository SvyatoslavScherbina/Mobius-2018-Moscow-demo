package sample.model

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

@Serializable
private class WikiResponse(val batchcomplete: String, val query: Query)

@Serializable private class Query(val geosearch: List<WikiPage>)

@Serializable class WikiPage(
    val pageid: Int,
    val ns: Int,
    val title: String,
    val lat: Double,
    val lon: Double,
    val dist: Double,
    val primary: String
)

class WikiApi {
    private val client = HttpClient() {
        install(JsonFeature) {

            serializer = KotlinxSerializer().apply {
                setMapper(WikiResponse::class, WikiResponse.serializer())
            }
        }
    }

    suspend fun getNearestPages(location: Location): List<WikiPage> {
        val url = "https://en.wikipedia.org/w/api.php" +
                "?action=query" +
                "&list=geosearch" +
                "&gscoord=${location.latitude}%7C${location.longitude}" +
                "&gsradius=10000" +
                "&gslimit=10" +
                "&format=json"

        return client.get<WikiResponse>(url).query.geosearch
    }

}

