package woowacourse.movie.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.notification.ReservationNotification.REQUEST_ID
import woowacourse.movie.notification.ReservationNotification.getNextNotificationTicketId
import woowacourse.movie.presentation.homefragments.setting.SettingFragment.Companion.PUSH_SETTING
import woowacourse.movie.presentation.ticketingResult.TicketingResultActivity
import woowacourse.movie.presentation.ticketingResult.TicketingResultActivity.Companion.TICKET_ID
import woowacourse.movie.repository.ReservationRepository
import woowacourse.movie.repository.ReservationRepositoryImpl

class ReservationNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        setBootReceiver(intent, context)
        if (!isPushOnState(context)) return

        val ticketId = intent?.getLongExtra(TICKET_ID_RECEIVER, -1L)
        val movieTitle = intent?.getStringExtra(MOVIE_TITLE) ?: ""
        val requestId = intent?.getIntExtra(REQUEST_ID, 0)

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        registrationChannel(notificationManager)

        val pendingIntent =
            makePendingIntent(
                context = context,
                ticketId = ticketId,
                requestId = requestId,
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
                NotificationManager.IMPORTANCE_HIGH,
            )
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildNotification(
        context: Context,
        movieTitle: String,
        pendingIntent: PendingIntent,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.movie_alarm)
            .setContentTitle("예매 알림")
            .setContentText("%s 30분 후에 상영".format(movieTitle))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun makePendingIntent(
        context: Context,
        ticketId: Long?,
        requestId: Int?,
    ): PendingIntent {
        val notificationIntent = Intent(context, TicketingResultActivity::class.java)
        val bundle = Bundle()
        bundle.putLong(TICKET_ID, ticketId!!)
        notificationIntent.putExtras(bundle)
        return PendingIntent.getActivity(
            context,
            requestId!!,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun setBootReceiver(
        intent: Intent?,
        context: Context?,
    ) {
        if (isBootingState(intent)) {
            context?.let { setNotificationTickets(it, ReservationRepositoryImpl(context)) }
        }
    }

    private fun isBootingState(intent: Intent?): Boolean {
        return intent?.action == Intent.ACTION_BOOT_COMPLETED
    }

    private fun setNotificationTickets(
        context: Context,
        repository: ReservationRepository,
    ) {
        val t =
            Thread {
                val tickets = repository.loadReservationList()
                tickets.forEach { reservationData ->
                    ReservationNotification.setNotification(
                        context = context,
                        ticketId = reservationData.id,
                        movieTitle = reservationData.movieTitle,
                        screeningDateTime = ("${reservationData.screenDate} ${reservationData.screenTime}"),
                    )
                }
            }
        t.start()
        t.join()
    }

    companion object {
        const val MOVIE_TITLE = "movieTitle"
        const val CHANNEL_ID = "ticket_notification_channel"
        const val CHANNEL_NAME = "Ticket Notifications"
        const val TICKET_ID_RECEIVER = "ticket_id"
    }
}
