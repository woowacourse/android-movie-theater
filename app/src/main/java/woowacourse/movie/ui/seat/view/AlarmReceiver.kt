package woowacourse.movie.ui.seat.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.domain.model.theater.Seats
import woowacourse.movie.ui.complete.view.BookingCompleteActivity
import woowacourse.movie.utils.intentSerializable
import java.time.LocalDateTime

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        createNotificationChannel(context)

        val channelId = CHANNEL_ID
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val bookedTicket: BookedTicket =
            intent.intentSerializable(EXTRA_BOOKED_TICKET, BookedTicket::class.java)
                ?: BookedTicket("", Headcount(), LocalDateTime.MIN, Seats(), "")
        val movieTitle: String = bookedTicket.movieName

        val notification =
            NotificationCompat
                .Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.text_notification_title))
                .setContentText("$movieTitle 30분 후에 상영")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(newPendingIntent(context, bookedTicket))
                .setAutoCancel(true)
                .build()

        notificationManager.notify(APP_ID, notification)
    }

    private fun newPendingIntent(
        context: Context,
        bookedTicket: BookedTicket,
    ): PendingIntent {
        val activityIntent = BookingCompleteActivity.newIntent(context, bookedTicket)
        return PendingIntent.getActivity(
            context,
            bookedTicket.hashCode(),
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    private fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        val notificationManager: NotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        fun newIntent(
            context: Context,
            bookedTicket: BookedTicket,
        ) = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_BOOKED_TICKET, bookedTicket)
        }

        private const val APP_ID = 1001
        private const val CHANNEL_ID = "CHANNEL_MOVIE_NOTIFICATION"
        private const val CHANNEL_NAME = "영화 알림 채널"
        private const val EXTRA_BOOKED_TICKET = "EXTRA_BOOKED_TICKET"
    }
}
