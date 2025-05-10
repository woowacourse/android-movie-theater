package woowacourse.movie.view.receiver

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.extension.alarmManager
import woowacourse.movie.view.extension.getParcelableCompatList
import woowacourse.movie.view.extension.notificationManager
import woowacourse.movie.view.extension.toEpochMilli
import woowacourse.movie.view.movies.reservation.result.ReservationResultActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val ticket = intent.getParcelableCompatList<Ticket>(TICKET_KEY)
        if (isEnabled) {
            sendNotification(context, ticket)
        }
    }

    private fun sendNotification(
        context: Context,
        ticket: Ticket,
    ) {
        val notificationManager = context.notificationManager()
        notificationManager.createNotificationChannel(channel)

        NotificationManagerCompat.from(context).apply {
            if (areNotificationsEnabled()) {
                notify(NOTIFICATION_ID, notification(ticket, context))
            }
        }
    }

    private fun notification(
        ticket: Ticket,
        context: Context,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(
                context.getString(R.string.notification_reservation),
            )
            .setContentText(
                context.getString(
                    R.string.notification_desc,
                    ticket.title,
                ),
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(
                ReservationResultActivity.pendingIntent(context, ticket),
            )
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "reservation_random_random"
        private const val CHANNEL_NAME = "reservation"
        private const val TICKET_KEY = "ticket"
        var isEnabled = false
            private set

        private val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )

        private fun newIntent(
            context: Context,
            ticket: Ticket,
        ): Intent {
            return Intent(context, NotificationReceiver::class.java).apply {
                putExtra(TICKET_KEY, ticket)
            }
        }

        private fun pendingIntent(
            context: Context,
            ticket: Ticket,
        ): PendingIntent {
            return PendingIntent.getBroadcast(
                context,
                0,
                newIntent(context, ticket),
                PendingIntent.FLAG_IMMUTABLE,
            )
        }

        fun cancelNotification() {
            isEnabled = false
        }

        fun setNotification(
            context: Context,
            ticket: Ticket,
        ) {
            isEnabled = true
            val alarmManager = context.alarmManager()
            if (AlarmManagerCompat.canScheduleExactAlarms(alarmManager)) {
                alarmManager.setExact(
                    AlarmManager.RTC,
                    ticket.showTime.toEpochMilli(),
                    pendingIntent(context, ticket),
                )
            }
        }
    }
}
