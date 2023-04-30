package woowacourse.movie.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class NotificationHelper(
    val context: Context,
    private val channelId: String,
    private val channelName: String
) {

    fun generateNotification(
        notificationData: NotificationData,
        notificationManager: NotificationManager
    ): Notification {
        val notificationBuilder = generateNotificationBuilder(notificationManager)
        bindNotification(notificationBuilder, notificationData)
        return notificationBuilder.build()
    }

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

    fun bindNotification(
        notificationBuilder: NotificationCompat.Builder,
        notificationData: NotificationData
    ) {
        notificationBuilder.setContentTitle(notificationData.title)
        notificationBuilder.setContentText(notificationData.contextText)
        notificationBuilder.setSmallIcon(notificationData.smallIcon)
        notificationBuilder.setAutoCancel(notificationData.isAutoCancel)
        notificationBuilder.setContentIntent(notificationData.pendingIntent)
    }

}
