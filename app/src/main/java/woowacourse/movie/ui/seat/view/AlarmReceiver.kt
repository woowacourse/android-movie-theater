package woowacourse.movie.ui.seat.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.utils.intentSerializable

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        createNotificationChannel(context)

        val channelId = "CHANNEL_MOVIE_NOTIFICATION"
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val bookedTicket: BookedTicket? =
            intent.intentSerializable(EXTRA_BOOKED_TICKET, BookedTicket::class.java)
        val movieTitle: String = bookedTicket?.movieName ?: ""

        val notification =
            NotificationCompat
                .Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // 알림 아이콘
                .setContentTitle(context.getString(R.string.text_notification_title))
                .setContentText("$movieTitle 30분 후에 상영") // 알림 내용
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        notificationManager.notify(1001, notification)
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

        private const val CHANNEL_ID = "CHANNEL_MOVIE_NOTIFICATION"
        private const val CHANNEL_NAME = "영화 알림 채널"
        private const val EXTRA_BOOKED_TICKET = "EXTRA_BOOKED_TICKET"
    }
}
