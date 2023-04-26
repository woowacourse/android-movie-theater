package woowacourse.movie.ui.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.ui.seat.SeatSelectionActivity
import woowacourse.movie.ui.ticket.MovieTicketActivity
import woowacourse.movie.utils.getSerializableExtraCompat

class ReservationAlarmReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE,
        ) as NotificationManager

        val movie: MovieTicketModel = intent.getSerializableExtraCompat(SettingFragment.KEY_MOVIE) ?: return
        createNotificationChannel()
        makeNotification(context, movie)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "reservation_alarm_channel",
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(
                notificationChannel,
            )
        }
    }

    private fun makeNotification(context: Context, movie: MovieTicketModel) {
        val contentIntent = Intent(context, MovieTicketActivity::class.java).apply {
            putExtra(SeatSelectionActivity.KEY_TICKET, movie)
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("예매 알림")
            .setContentText("${movie.title} 30분 후에 상영")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 0
    }
}
