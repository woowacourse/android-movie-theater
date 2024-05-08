package woowacourse.movie

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import woowacourse.movie.data.db.ReservationDatabase

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ReservationDatabase.getInstance(applicationContext)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }
}
