package woowacourse.movie.ui.view.receiver

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
import woowacourse.movie.ui.view.alarm.Alarm.Companion.EXTRA_ALARM_TICKET_ID
import woowacourse.movie.ui.view.ticket.TicketActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (context == null || intent == null) return
        val ticketId = intent.getLongExtra(EXTRA_ALARM_TICKET_ID, -1)
        val ticket = intent.getTicketExtra(ticketId.toString()) ?: return
        showNotification(context, ticket)
    }

    private fun showNotification(
        context: Context,
        ticket: Ticket,
    ) {
        val channelId = "alarm_channel"
        val channel =
            NotificationChannel(
                channelId,
                context.getString(R.string.notification_ticket_title),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "영화 상영전 알림"
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

        val resultPendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                ticketIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notification =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle(context.getString(R.string.notification_ticket_title))
                .setContentText(context.getString(R.string.notification_ticket_text, ticket.title))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(1001, notification)
    }

    @Suppress("DEPRECATION")
    private fun Intent.getTicketExtra(key: String): Ticket? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                getSerializableExtra(key, Ticket::class.java)

            else -> getSerializableExtra(key) as? Ticket
        }
}
