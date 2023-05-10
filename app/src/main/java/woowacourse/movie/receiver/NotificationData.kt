package woowacourse.movie.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat

data class NotificationData(
    val title: String,
    val contextText: String,
    val smallIcon: Int,
    val isAutoCancel: Boolean,
    val pendingIntent: PendingIntent
) {
    fun generateNotificationBuilder(
        context: Context,
        channelId: String
    ): NotificationCompat.Builder {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(contextText)
        notificationBuilder.setSmallIcon(smallIcon)
        notificationBuilder.setAutoCancel(isAutoCancel)
        notificationBuilder.setContentIntent(pendingIntent)
        return notificationBuilder
    }
}
