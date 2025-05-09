package woowacourse.movie.view.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (context != null && intent != null) {
            val title = intent.getStringExtra("notification_title") ?: "알림 제목"
            val text = intent.getStringExtra("notification_text") ?: "알림 내용"
            sendNotification(context, title, text)
        }
    }

    private fun sendNotification(
        context: Context,
        title: String,
        text: String,
    ) {
        val channelId = "my_alarm_channel"
        val notificationId = 1 // 알림 ID (여러 알림 구분용)
        val name = title
        val descriptionText = text
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val builder =
            NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (areNotificationsEnabled()) {
                notify(notificationId, builder.build())
            }
        }
    }
}
