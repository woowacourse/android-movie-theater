package woowacourse.movie.view.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationDbHelper
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.domain.screening.Reservation
import woowacourse.movie.view.activities.reservationresult.ReservationResultActivity
import java.time.Duration
import java.time.LocalDateTime

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val reservationId = intent.getLongExtra(RESERVATION_ID, -1)

        val reservationRepository =
            ReservationRepositoryImpl(ReservationDbHelper.getDbInstance(context))
        val reservation = reservationRepository.findById(reservationId)

        val reservationResultIntent = createReservationResultIntent(context, reservationId)

        notifyThatScreeningWillStartSoon(context, reservation, reservationResultIntent)
    }

    private fun createReservationResultIntent(
        context: Context,
        reservationId: Long
    ): PendingIntent =
        Intent(context, ReservationResultActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(ReservationResultActivity.RESERVATION_ID, reservationId)
        }.let {
            PendingIntent.getActivity(context, 0, it, FLAG_IMMUTABLE)
        }

    private fun notifyThatScreeningWillStartSoon(
        context: Context,
        reservation: Reservation,
        notificationContentIntent: PendingIntent
    ) {
        NotificationCompat.Builder(context, "1").apply {
            setSmallIcon(R.drawable.baseline_notifications_24)
            setContentTitle(context.getString(R.string.reservation_notification))
            setContentText(
                context.getString(R.string.reservation_notification_message_format).format(
                    reservation.movie.title,
                    Duration.between(LocalDateTime.now(), reservation.screeningDateTime).toMinutes()
                )
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(notificationContentIntent)
            setAutoCancel(true)
        }.let {
            val notificationManager = getNotificationManager(context)

            notificationManager.notify(1, it.build())
        }
    }

    private fun getNotificationManager(context: Context): NotificationManager =
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).also {
            val name = "getString(R.string.channel_name)"
            val descriptionText = "getString(R.string.channel_description)"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            it.createNotificationChannel(channel)
        }

    companion object {
        const val RESERVATION_ID = "RESERVATION_ID"
    }
}
