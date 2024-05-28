package woowacourse.movie.ui.pushnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.MovieReservationApplication
import woowacourse.movie.R
import woowacourse.movie.ui.main.MainActivity
import woowacourse.movie.ui.reservation.ReservationCompleteActivity
import woowacourse.movie.ui.reservation.ReservationCompleteActivity.Companion.DEFAULT_RESERVATION_TICKET_ID
import woowacourse.movie.ui.reservation.ReservationCompleteActivity.Companion.PUT_EXTRA_KEY_RESERVATION_TICKET_ID

class PushNotificationBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservationTicketId = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, DEFAULT_RESERVATION_TICKET_ID)
        val pendingIntent = createPendingIntent(context, reservationTicketId)

        val notification = buildNotification(context, pendingIntent)

        val notificationEnabled = MovieReservationApplication.notificationPreference.loadNotificationPreference()
        if (notificationEnabled) {
            notify(context, notification)
        }
    }

    private fun createPendingIntent(
        context: Context,
        reservationTicketId: Int,
    ): PendingIntent {
        val mainIntent = Intent(context, MainActivity::class.java)
        val notificationIntent =
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, reservationTicketId)
            }
        val stackBuilder =
            TaskStackBuilder.create(context).apply {
                addParentStack(MainActivity::class.java)
                addNextIntentWithParentStack(mainIntent)
                addNextIntent(notificationIntent)
            }

        return stackBuilder.getPendingIntent(
            MOVIE_RESERVATION_REMINDER_REQUEST_CODE,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    private fun buildNotification(
        context: Context,
        pendingIntent: PendingIntent,
    ): Notification =
        NotificationCompat.Builder(context, MOVIE_RESERVATION_REMINDER_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.movie_reservation_reminder_title))
            .setContentText(context.getString(R.string.movie_reservation_reminder_content, 30))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

    private fun notify(
        context: Context,
        notification: Notification,
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(
                MOVIE_RESERVATION_REMINDER_CHANNEL_ID,
                MOVIE_RESERVATION_REMINDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(MOVIE_RESERVATION_REMINDER_REQUEST_CODE, notification)
    }

    companion object {
        private const val MOVIE_RESERVATION_REMINDER_CHANNEL_ID = "movie_reservation_reminder_channel_id"
        private const val MOVIE_RESERVATION_REMINDER_CHANNEL_NAME = "movie_reservation_reminder_channel_name"
        const val MOVIE_RESERVATION_REMINDER_REQUEST_CODE = 101
    }
}
