package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.db.ReservationDatabase

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ReservationDatabase.getInstance(applicationContext)
    }
}
