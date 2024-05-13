package woowacourse

import android.app.Application
import woowacourse.movie.data.db.ReservationHistoryDatabase
import woowacourse.movie.util.SharedPrefs

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        database = ReservationHistoryDatabase.getInstance(applicationContext)
        sharedPrefs = SharedPrefs(applicationContext)
    }

    companion object {
        lateinit var database: ReservationHistoryDatabase
        lateinit var sharedPrefs: SharedPrefs
    }
}
