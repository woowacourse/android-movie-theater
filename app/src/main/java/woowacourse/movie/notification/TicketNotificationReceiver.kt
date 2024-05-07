package woowacourse.movie.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.notification.TicketNotification.NOTIFICATION_ID
import woowacourse.movie.notification.TicketNotification.PENDING_REQUEST_CODE
import woowacourse.movie.utils.MovieUtils.intentSerializable
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.TICKET
import woowacourse.movie.view.result.ReservationResultActivity

class TicketNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val ticket = intent?.intentSerializable(TICKET, Ticket::class.java)
        val movieTitle = intent?.getStringExtra(MOVIE_TITLE) ?: ""

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notificationIntent = Intent(context, ReservationResultActivity::class.java)
        notificationIntent.putExtra(TICKET, ticket)
        val pendingIntent = PendingIntent.getActivity(
            context,
            PENDING_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(ALARM_TITLE)
            .setContentText(ALARM_TEXT.format(movieTitle))
            .setSmallIcon(R.drawable.home_icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val MOVIE_TITLE = "movieTitle"
        const val CHANNEL_ID = "ticket_notification_channel"
        const val CHANNEL_NAME = "Ticket Notifications"
        const val ALARM_TITLE ="예매 알림"
        const val ALARM_TEXT = "%s 30분 후에 상영"
    }
}
