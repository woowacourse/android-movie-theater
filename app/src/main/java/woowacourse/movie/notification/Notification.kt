package woowacourse.movie.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.model.Reservation
import woowacourse.movie.presentation.home.HomeActivity
import woowacourse.movie.presentation.ticketingResult.TicketingResultActivity

class Notification(context: Context) {
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

    fun buildNotification(
        context: Context,
        reservation: Reservation,
    ) {
        val pendingIntent = createPendingIntent(context, reservation)
        val builder = createReservationNotificationBuilder(context, reservation)
        builder.setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED &&
                HomeActivity.sharedPreference.isPushNotificationActivated()
            ) {
                notify(1, builder.build())
            }
        }
    }

    private fun createReservationNotificationBuilder(
        context: Context,
        reservation: Reservation,
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle("예매 알림")
            .setContentText("${reservation.movieTitle} 30분 뒤 상영 예정")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun createPendingIntent(
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
