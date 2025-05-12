package woowacourse.movie.presentation.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.presentation.bookingsummary.BookingSummaryActivity

class NotificationHelper(private val context: Context) {
    fun showMovieTicketReminder(
        ticket: MovieTicket,
        time: Long,
    ) {
        createNotificationChannel()
        val pendingIntent = createClickIntent(ticket)
        val notification = createNotification(pendingIntent, ticket, time)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ticket.hashCode(), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createClickIntent(ticket: MovieTicket): PendingIntent {
        val intent = BookingSummaryActivity.newIntent(context, ticket).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return PendingIntent.getActivity(
            context,
            ticket.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotification(
        intent: PendingIntent,
        ticket: MovieTicket,
        time: Long,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_description).format(ticket.movieTitle, time))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()
    }

    companion object {
        const val CHANNEL_ID = "TICKET_REMINDER"
    }
}