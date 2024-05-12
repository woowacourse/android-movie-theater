package woowacourse.movie.presentation.view.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.view.notification.MovieNotificationAlarmManager.NOTIFICATION_TITLE
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity

class MovieNotificationReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager


    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MovieNotificationReceiver", "Notification received")
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        subScribeToChannel()
        val pendingIntent = createPendingIntent(context)

        val title = intent.getStringExtra(NOTIFICATION_TITLE) ?: " "
        val notification = buildNotification(
            context = context,
            title = title,
            pendingIntent = pendingIntent,
        )
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    // channel 등록
    private fun subScribeToChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_DEFAULT
        )
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel) // 채널을 시스템에 등록
    }

    private fun createPendingIntent(
        context: Context,
    ): PendingIntent {
        val contentIntent = Intent(context, ReservationResultActivity::class.java)
        return PendingIntent.getActivity(
            context,
            PENDING_REQUEST_CODE,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    // 알람만들기
    private fun buildNotification(
        context: Context,
        title: String,
        pendingIntent: PendingIntent,
    ): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie_alarm)
            .setContentTitle("예매 내역")
            .setContentText(context.getString(R.string.notification_content, title))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }


    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "notification_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "movieNotification"
        const val NOTIFICATION_ID = 1
        const val PENDING_REQUEST_CODE = 0
    }
}
