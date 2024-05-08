package woowacourse.movie

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.presentation.message.AndroidMessage
import woowacourse.movie.presentation.message.Messenger

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ReservationDatabase.getInstance(applicationContext)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        Messenger.init(AndroidMessage(this))
    }

    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }
}
