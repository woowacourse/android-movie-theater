package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.TicketInfoDatabase
import woowacourse.movie.view.setting.notification.NotificationHelper

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        TicketInfoDatabase.getDatabase(this)
        NotificationHelper.createNotificationChannel(this)
    }
}
