package woowacourse.movie.presentation

import android.app.Application
import com.woowacourse.data.DataStore
import com.woowacourse.data.local.LocalDataStore

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        dataStore = LocalDataStore(applicationContext)
    }

    companion object {
        internal lateinit var dataStore: DataStore
    }
}
