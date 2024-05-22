package woowacourse.movie

import android.app.Application
import woowacourse.movie.preference.NotificationPreference
import woowacourse.movie.preference.NotificationSharedPreferences

class MovieReservationApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        notificationPreference = NotificationSharedPreferences.getInstance(applicationContext)
    }

    companion object {
        lateinit var notificationPreference: NotificationPreference
            private set
    }
}
