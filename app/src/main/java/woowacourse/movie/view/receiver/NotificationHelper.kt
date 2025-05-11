package woowacourse.movie.view.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.view.complete.BookingCompleteActivity

class NotificationHelper(private val context: Context) {
    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        registrationNotificationChannel()
    }

    fun notification(
        ticketId: Long,
        movieTitle: String,
    ): Notification {
        val intent = BookingCompleteActivity.newIntent(context, ticketId)
        val pendingIntent =
            PendingIntent.getActivity(context, 10, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notice)
            .setContentTitle(context.getString(R.string.notification_booking_tile))
            .setContentText(context.getString(R.string.notification_movie_guide).format(movieTitle))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun notify(
        id: Int,
        notification: Notification,
    ) {
        manager.notify(id, notification)
    }

    private fun registrationNotificationChannel() {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "ALARM_CHANNEL_ID"
        private const val CHANNEL_NAME = "ALARM_CHANNEL_NAME"
    }
}
