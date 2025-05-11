package woowacourse.movie.presentation.notification.ticket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.presentation.ticket.detail.TicketDetailActivity

class TicketNotification(
    private val context: Context,
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    fun sendNotification(ticket: Ticket) {
        val intent = TicketDetailActivity.newIntent(context, ticket)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )

        val builder =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("예매 알림")
                .setContentText("${ticket.movie.title} 30분 후에 상영")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        val notification = builder.build()
        notificationManager.notify(ticket.hashCode(), notification)
    }

    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "channel_notification"
        private const val CHANNEL_NAME = "영화 상영 알림"
    }
}
