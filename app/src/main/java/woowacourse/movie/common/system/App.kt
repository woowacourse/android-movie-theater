package woowacourse.movie.common.system

import android.app.Application
import woowacourse.movie.common.database.MovieDao

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        movieDao = MovieDao(applicationContext)
    }
    companion object {
        lateinit var movieDao: MovieDao
    }
}