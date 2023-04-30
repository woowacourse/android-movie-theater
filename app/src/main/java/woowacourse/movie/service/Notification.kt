package woowacourse.movie.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.service.ReservationAlarmReceiver.Companion.requestCode
import woowacourse.movie.view.data.ReservationViewData

object Notification {

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

    fun makeNotificationPendingIntent(
        context: Context,
        reservation: ReservationViewData
    ): PendingIntent {
        val reservationIntent = ReservationResultActivity.from(context, reservation).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context,
            requestCode,
            reservationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun makeNotificationBuilder(
        context: Context,
        reservation: ReservationViewData,
        pendingIntent: PendingIntent
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
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
    }

    fun notifyNotification(context: Context, builder: NotificationCompat.Builder) {
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(notificationId, builder.build())
            }
        }
    }
}
