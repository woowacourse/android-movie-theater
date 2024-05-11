package woowacourse.movie.feature.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.feature.notification.ScreeningAlarm.Companion.NOTIFICATION_TEXT
import woowacourse.movie.feature.notification.ScreeningAlarm.Companion.NOTIFICATION_TITLE

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notification = ScreeningNotification(context)

        val title = intent.getStringExtra(NOTIFICATION_TITLE)
        val description = intent.getStringExtra(NOTIFICATION_TEXT)

        if (title != null && description != null) {
            notification.deliver(intent, title, description)
        }
    }
}
