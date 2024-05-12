package woowacourse.movie.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.reservationresult.ReservationResultActivity

class AlarmReceiver() : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val title = intent.getStringExtra(TITLE_ID) ?: error("영화 제목에 대한 정보가 없습니다.")
        val id = intent.getLongExtra(CHANNEL_ID, INVALID_CHANNEL_ID)

        createNotificationChannel()
        deliverNotification(context, title, id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH,
                )
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(
                notificationChannel,
            )
        }
    }

    private fun deliverNotification(
        context: Context,
        title: String,
        id: Long,
    ) {
        val contentIntent = ReservationResultActivity.getIntent(context, id)
        val contentPendingIntent =
            PendingIntent.getActivity(
                context,
                NOTIFICATION_ID,
                contentIntent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("예매 알림")
                .setContentText("$title 30분 후에 상영")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    companion object {
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "alarm_id"
        private const val CHANNEL_NAME = "alam"
        private const val INVALID_CHANNEL_ID = -1L

        const val TITLE_ID = "titleId"

        fun newIntent(
            context: Context,
            title: String,
            id: Long,
        ): Intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(TITLE_ID, title)
                putExtra(CHANNEL_ID, id)
            }
    }
}
