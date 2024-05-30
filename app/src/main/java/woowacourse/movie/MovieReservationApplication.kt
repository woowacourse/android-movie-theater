package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.source.NotificationPreference
import woowacourse.movie.local.source.NotificationSharedPreferences

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
