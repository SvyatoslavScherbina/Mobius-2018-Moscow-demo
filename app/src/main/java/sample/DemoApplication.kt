package sample

import android.app.Application
import sample.model.*

class DemoApplication : Application() {
    val locationService by lazy { LocationServiceImpl(this) }
    val wikiRepository = WikiRepositoryImpl()
}