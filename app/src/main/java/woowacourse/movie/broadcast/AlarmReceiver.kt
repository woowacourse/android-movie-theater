package woowacourse.movie.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import woowacourse.movie.R
import woowacourse.movie.model.ReservationResult
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val reservationResult =
            intent?.getParcelableCompat<ReservationResult>(RESERVATION_INTENT_KEY) ?: return

        ReservationRepository.findById(reservationResult.id) ?: ReservationRepository.save(
            Reservation(
                reservationResult.totalPrice,
                reservationResult.ticketCount,
                reservationResult.seatNames,
                reservationResult.movieTitle,
                reservationResult.date,
                reservationResult.time
            )
        )

        val activityIntent = Intent(
            context, BookCompleteActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(
                BookCompleteActivity.RESERVATION_ID_INTENT_KEY,
                intent.getLongExtra(
                    BookCompleteActivity.RESERVATION_ID_INTENT_KEY,
                    -1L
                )
            )
        }

        val pendingIntent = PendingIntent.getActivity(
            context!!, 0, activityIntent, FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val name = "getString(R.string.channel_name)"
        val descriptionText = "getString(R.string.channel_description)"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system

        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(1, builder.build())
    }

    companion object {
        const val RESERVATION_INTENT_KEY = "RESERVATION_INTENT_KEY"
    }

}
