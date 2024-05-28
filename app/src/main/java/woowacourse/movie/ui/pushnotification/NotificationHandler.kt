package woowacourse.movie.ui.pushnotification

import android.app.Notification
import android.app.PendingIntent

interface NotificationHandler {
    fun createPendingIntent(id: Int): PendingIntent

    fun buildNotification(pendingIntent: PendingIntent): Notification

    fun notify(notification: Notification)
}
