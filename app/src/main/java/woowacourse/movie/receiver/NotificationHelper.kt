package woowacourse.movie.receiver

import android.app.Notification
import android.app.Notification.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHelper(
    val context: Context,
    private val channelId: String,
    private val channelName: String
) {
    fun generateNotificationManger(): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun generateNotificationBuilder(
        notificationManager: NotificationManager
    ): NotificationCompat.Builder {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )
        return NotificationCompat.Builder(context, channelId)
    }

    fun generateNotification(notificationBuilder: NotificationCompat.Builder): Notification {
        return notificationBuilder.build()
    }

    fun bindNotification(
        notificationBuilder: NotificationCompat.Builder,
        title: String,
        contextText: String,
        smallIcon: Int,
        isAutoCancel: Boolean,
        pendingIntent: PendingIntent
    ) {
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(contextText)
        notificationBuilder.setSmallIcon(smallIcon)
        notificationBuilder.setAutoCancel(isAutoCancel)
        notificationBuilder.setContentIntent(pendingIntent)
    }

}
