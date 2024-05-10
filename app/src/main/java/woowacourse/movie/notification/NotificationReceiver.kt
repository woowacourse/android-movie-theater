package woowacourse.movie.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.MovieApplication.Companion.sharedPreferences
import woowacourse.movie.R
import woowacourse.movie.notification.NotificationHandler.Companion.NOTIFICATION_ID
import woowacourse.movie.notification.NotificationHandler.Companion.PENDING_REQUEST_CODE
import woowacourse.movie.notification.NotificationHandler.Companion.PUT_EXTRA_KEY_MOVIE_TITLE_ID
import woowacourse.movie.notification.NotificationHandler.Companion.PUT_EXTRA_KEY_RESERVATION_ID
import woowacourse.movie.presentation.ui.main.MainActivity
import woowacourse.movie.presentation.ui.reservation.ReservationActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservationId =
            intent.getLongExtra(PUT_EXTRA_KEY_RESERVATION_ID, DEFAULT_RESERVATION_ID)
        val movieTitle = intent.getStringExtra(PUT_EXTRA_KEY_MOVIE_TITLE_ID) ?: ""

        val pendingIntent = createPendingIntent(context, reservationId)
        val notification = createNotification(context, pendingIntent, movieTitle)
        val notificationMode = sharedPreferences.getBoolean(KEY_NOTIFICATION_MODE, false)

        if (notificationMode) {
            notifyNotification(context, notification)
        }
    }

    private fun createPendingIntent(
        context: Context,
        reservationId: Long,
    ): PendingIntent {
        val mainIntent = Intent(context, MainActivity::class.java)
        val notificationIntent =
            Intent(context, ReservationActivity::class.java).apply {
                putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
            }

        val stackBuilder =
            TaskStackBuilder.create(context).apply {
                addParentStack(MainActivity::class.java)
                addNextIntentWithParentStack(mainIntent)
                addNextIntent(notificationIntent)
            }

        return stackBuilder.getPendingIntent(
            PENDING_REQUEST_CODE,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun createNotification(
        context: Context,
        pendingIntent: PendingIntent,
        movieTitle: String,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_description, movieTitle))
            .setSmallIcon(R.drawable.ic_notification).setContentIntent(pendingIntent)
            .setAutoCancel(true).build()
    }

    private fun notifyNotification(
        context: Context,
        notification: Notification,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val KEY_NOTIFICATION_MODE = "KEY_NOTIFICATION_MODE"

        const val DEFAULT_RESERVATION_ID = -1L
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "Notifications"
    }
}
