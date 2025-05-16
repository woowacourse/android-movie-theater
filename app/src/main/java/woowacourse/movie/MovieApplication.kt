package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.data.dummy.DUMMY_ENTITY_MOVIES
import kotlin.concurrent.thread

class MovieApplication : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(applicationContext)
        thread {
            database.movieDao().insertAll(*DUMMY_ENTITY_MOVIES)
        }
    }
}
