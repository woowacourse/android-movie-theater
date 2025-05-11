package woowacourse.movie

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.helper.BuildVersion
import woowacourse.movie.moviebooked.MovieBookedActivity

class ReservationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bookingStatus = BuildVersion().getParcelableClass(
            intent,
            "booking_status",
            BookingStatus::class
        )
        showNotification(context, bookingStatus)
    }

    private fun showNotification(context: Context, bookingStatus: BookingStatus) {
        val detailIntent = MovieBookedActivity.movieBookedIntent(context, bookingStatus).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            bookingStatus.hashCode(),
            detailIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, "reservation_channel_id")
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle("예매 알림")
            .setContentText("${bookingStatus.movie.title} 30분 후 상영!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            with(NotificationManagerCompat.from(context)) {
                notify(bookingStatus.hashCode(), builder.build())
            }
        }
    }
}
