package woowacourse.movie.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import woowacourse.movie.R

class BookingNotification(private val context: Context, private val pendingIntent: PendingIntent) {
    fun sendNotification(movieTitle: String) {
        val builder = NotificationCompat.Builder(context, "1")
        setNotificationInfo(builder, movieTitle)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESCRIPTION
        }

        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(CHANNEL_ID.toInt(), builder.build())
    }

    private fun setNotificationInfo(builder: NotificationCompat.Builder, movieTitle: String) {
        builder.setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(context.getString(R.string.booking_notification_title))
            .setContentText(
                context.getString(R.string.booking_notification_content).format(movieTitle)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    companion object {
        private const val CHANNEL_ID = "1"
        private const val CHANNEL_NAME = "BOOKING_NOTIFICATION_CHANNEL"
        private const val CHANNEL_DESCRIPTION = "BOOKING_NOTIFICATION_CHANNEL"
    }

}