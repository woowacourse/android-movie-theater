package woowacourse.movie

import android.app.Application
import woowacourse.movie.service.NotificationManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationManager.createNotificationChannel(this)
    }
}
