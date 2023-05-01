package woowacourse.movie.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.service.PermissionManager.checkNotificationSelfPermission
import woowacourse.movie.service.ReservationAlarmReceiver.Companion.requestCode
import woowacourse.movie.view.data.ReservationViewData

object NotificationManager {

    private const val RESERVATION_NOTIFICATION_CHANNEL_ID = "reservation"
    private val notificationId: Int
        get() = System.currentTimeMillis().toInt()

    fun createNotificationChannel(context: Context?) {
        val channel = NotificationChannel(
            RESERVATION_NOTIFICATION_CHANNEL_ID,
            context?.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context?.getString(R.string.notification_channel_description)
        }

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun notifyNotification(context: Context, reservation: ReservationViewData) {
        with(NotificationManagerCompat.from(context)) {
            if (!context.checkNotificationSelfPermission()) {
                val builder = makeNotificationBuilder(context, reservation)
                notify(notificationId, builder.build())
            }
        }
    }

    private fun makeNotificationPendingIntent(
        context: Context,
        reservation: ReservationViewData
    ): PendingIntent {
        val reservationIntent = ReservationResultActivity.from(context, reservation)
        return PendingIntent.getActivity(
            context,
            requestCode,
            reservationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun makeNotificationBuilder(
        context: Context,
        reservation: ReservationViewData
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context,
            RESERVATION_NOTIFICATION_CHANNEL_ID
        ).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(context.getString(R.string.notification_content_title))
            setContentText(
                context.getString(
                    R.string.notification_content_text, reservation.movie.title
                )
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
            val pendingIntent = makeNotificationPendingIntent(context, reservation)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
    }
}
