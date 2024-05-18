package woowacourse.movie.presentation.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.IntentCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.reservation.result.ReservationResultActivity.Companion.INTENT_TICKET
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel

class MovieNotificationReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        notificationManager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE,
            ) as NotificationManager

        subScribeToChannel()

        val ticket =
            IntentCompat.getParcelableExtra(
                intent,
                INTENT_TICKET,
                MovieTicketUiModel::class.java,
            )
        val pendingIntent = createPendingIntent(context, ticket)

        val title = ticket!!.title

        val notification =
            buildNotification(
                context = context,
                title = title,
                pendingIntent = pendingIntent,
            )
        val requestId = createNotificationId()
        notificationManager.notify(requestId, notification)
    }

    private fun subScribeToChannel() {
        val channel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_DEFAULT,
            )
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createPendingIntent(
        context: Context,
        ticket: MovieTicketUiModel?,
    ): PendingIntent {
        val contentIntent =
            getIntent(context).apply {
                putExtra(INTENT_TICKET, ticket)
            }
        return PendingIntent.getActivity(
            context,
            PENDING_REQUEST_CODE,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun buildNotification(
        context: Context,
        title: String,
        pendingIntent: PendingIntent,
    ): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie_alarm)
            .setContentTitle(context.getString(R.string.notification_content_title))
            .setContentText(context.getString(R.string.notification_content, title))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun createNotificationId(): Int = System.currentTimeMillis().toInt()

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "notification_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "movieNotification"
        const val PENDING_REQUEST_CODE = 0

        fun getIntent(context: Context): Intent {
            return Intent(context, MovieNotificationReceiver::class.java)
        }
    }
}
