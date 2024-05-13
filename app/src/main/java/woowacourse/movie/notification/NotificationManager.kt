package woowacourse.movie.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.Reservation
import woowacourse.movie.presentation.ticketingResult.TicketingResultActivity

class NotificationManager(context: Context) {
    private val notificationManager =
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManager.createNotificationChannel(channel)
    }

    fun buildReservationNotification(
        context: Context,
        reservation: Reservation,
    ): Notification {
        val pendingIntent = createTicketingResultPendingIntent(context, reservation)
        val builder = createReservationNotificationBuilder(context, reservation, pendingIntent)

        return builder.build()
    }

    private fun createReservationNotificationBuilder(
        context: Context,
        reservation: Reservation,
        pendingIntent: PendingIntent,
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle("예매 알림")
            .setContentText("${reservation.movieTitle} 30분 뒤 상영 예정")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
    }

    private fun createTicketingResultPendingIntent(
        context: Context,
        reservation: Reservation,
    ): PendingIntent {
        val intent =
            TicketingResultActivity.createIntent(context, reservation)
        return PendingIntent.getActivity(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notification_channel"
        const val NOTIFICATION_CHANNEL_NAME = "movieNotification"
        const val NOTIFICATION_REQUEST_CODE = 0
    }
}
