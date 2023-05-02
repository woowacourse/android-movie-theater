package woowacourse.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class MovieNotificationManager(context: Context) {

    private val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager

    fun createChannel(notificationChannel: NotificationChannel) {
        notificationManager.createNotificationChannel(
            notificationChannel
        )
    }

    fun notify(id: Int, builder: NotificationCompat.Builder) {
        notificationManager.notify(id, builder.build())
    }
}
