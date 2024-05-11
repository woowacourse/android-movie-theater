package woowacourse.movie.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.notification.TicketNotification.PENDING_REQUEST_CODE
import woowacourse.movie.notification.TicketNotification.getNextNotificationTicketId
import woowacourse.movie.repository.ReservationTicketRepository
import woowacourse.movie.repository.ReservationTicketRepositoryImpl
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.DEFAULT_TICKET_ID
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.RESERVATION_TICKET_ID
import woowacourse.movie.view.result.ReservationResultActivity
import woowacourse.movie.view.setting.SettingFragment.Companion.PUSH_SETTING

class TicketNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        setBootReceiver(intent, context)
        if (!isPushOnState(context)) return

        val ticketId = intent?.getLongExtra(RESERVATION_TICKET_ID, DEFAULT_TICKET_ID)
        val movieTitle = intent?.getStringExtra(MOVIE_TITLE) ?: ""

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        registrationChannel(notificationManager)

        val pendingIntent =
            makePendingIntent(
                context = context,
                ticketId = ticketId,
            )

        val notification =
            buildNotification(
                context = context,
                movieTitle = movieTitle,
                pendingIntent = pendingIntent,
            )
        notificationManager.notify(getNextNotificationTicketId(context), notification)
    }

    private fun isPushOnState(context: Context?): Boolean {
        val sharedPreferences = context?.getSharedPreferences(PUSH_SETTING, Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean(PUSH_SETTING, false) ?: false
    }

    private fun registrationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildNotification(
        context: Context,
        movieTitle: String,
        pendingIntent: PendingIntent,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.ticket_alarm_title))
            .setContentText(context.getString(R.string.ticket_alarm_text).format(movieTitle))
            .setSmallIcon(R.drawable.movie_filter_24dp)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun makePendingIntent(
        context: Context,
        ticketId: Long?,
    ): PendingIntent {
        val notificationIntent = Intent(context, ReservationResultActivity::class.java)
        notificationIntent.putExtra(RESERVATION_TICKET_ID, ticketId)
        return PendingIntent.getActivity(
            context,
            PENDING_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun setBootReceiver(
        intent: Intent?,
        context: Context?,
    ) {
        if (isBootingState(intent)) {
            context?.let { setNotificationTickets(it, ReservationTicketRepositoryImpl(it)) }
        }
    }

    private fun isBootingState(intent: Intent?): Boolean {
        return intent?.action == Intent.ACTION_BOOT_COMPLETED
    }

    private fun setNotificationTickets(
        context: Context,
        repository: ReservationTicketRepository,
    ) {
        Thread {
            val tickets = repository.loadReservationTickets()
            tickets.forEach { reservationTicket ->
                TicketNotification.setNotification(
                    context = context,
                    ticketId = reservationTicket.ticketId,
                    movieTitle = reservationTicket.movieTitle,
                    screeningDateTime = reservationTicket.screeningDateTime,
                )
            }
        }.start()
    }

    companion object {
        const val MOVIE_TITLE = "movieTitle"
        const val CHANNEL_ID = "ticket_notification_channel"
        const val CHANNEL_NAME = "Ticket Notifications"
    }
}
