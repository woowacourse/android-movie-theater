package woowacourse.movie.ui.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.alarm.Alarm.Companion.EXTRA_ALARM_TICKET_ID
import woowacourse.movie.ui.view.ticket.TicketActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val ticketId = intent.getLongExtra(EXTRA_ALARM_TICKET_ID, -1)
        val ticket = intent.getTicketExtra(ticketId.toString()) ?: return
        showTicketNotification(context, ticket)
    }

    private fun showTicketNotification(
        context: Context,
        ticket: Ticket,
    ) {
        if (ticket.id == null) return
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_ticket_title),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = context.getString(R.string.notification_ticket_description)
            }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        val ticketIntent =
            ticket.run {
                TicketActivity.newIntent(
                    context,
                    title,
                    count,
                    showtime,
                    cinemaName,
                    seats,
                    purchaseType,
                )
            }

        val ticketPendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                ticketIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notification = ticketNotification(context, ticket, ticketPendingIntent)

        notificationManager.notify(ticket.id.toInt(), notification)
    }

    private fun ticketNotification(
        context: Context,
        ticket: Ticket,
        pendingIntent: PendingIntent,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm_icon)
            .setContentTitle(context.getString(R.string.notification_ticket_title))
            .setContentText(context.getString(R.string.notification_ticket_text, ticket.title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    @Suppress("DEPRECATION")
    private fun Intent.getTicketExtra(key: String): Ticket? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                getSerializableExtra(key, Ticket::class.java)

            else -> getSerializableExtra(key) as? Ticket
        }

    companion object {
        private const val CHANNEL_ID = "id_ticket_alarm_channel"
    }
}
