package woowacourse.movie.view.alarm

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.reservationcompleted.ReservationCompletedActivity

class ReservationNotificationManager(
    private val context: Context
) {

    fun notify(
        reservation: ReservationUiModel
    ) {
        val content = context.getString(R.string.screening_after_30_minutes, reservation.title)
        val reservationPendingIntent = makeReservationActivityPendingIntent(reservation)
        val notification = makeNotification(content, reservationPendingIntent)
        return notify(notification)
    }

    private fun notify(notification: Notification) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context)
                .notify(NOTIFICATION_ID, notification)
            return
        }
    }

    private fun makeNotification(
        content: String,
        reservationPendingIntent: PendingIntent
    ) = NotificationCompat.Builder(context, AlarmController.CHANNEL_ID).apply {
        setSmallIcon(R.drawable.ic_movie)
        setContentTitle(context.getString(R.string.reservation_noti))
        setContentText(content)
        setContentIntent(reservationPendingIntent)
        setAutoCancel(true)
    }.build()

    private fun makeReservationActivityPendingIntent(reservation: ReservationUiModel) =
        PendingIntent.getActivity(
            context,
            ReservationCompletedActivity.REQUEST_CODE,
            ReservationCompletedActivity.newIntent(context, reservation),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

    companion object {
        private const val NOTIFICATION_ID = 200
    }
}
