package woowacourse.movie.ui.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import woowacourse.movie.R

class MovieAlarmNotificationBuilder(private val context: Context) {
    private val builder by lazy { notificationBuilder(context) }
    private val manager by lazy { notificationManager(context) }

    fun notifyAlarm() = manager.notify(NOTIFICATION_ID, builder.build())

    private fun notificationManager(context: Context): NotificationManager {
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel())

        notificationBuilder(context)
        return manager
    }

    private fun notificationBuilder(context: Context): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)

        builder.apply {
            setSmallIcon(R.drawable.thumbnail_movie2)
            setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.thumbnail_movie2
                )
            )
            setContentTitle("예매 알림")
        }

        return builder
    }

    private fun notificationChannel(): NotificationChannel {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Little Description"
            }
        return channel
    }

    companion object {
        private const val CHANNEL_ID = "one-channel"
        private const val CHANNEL_NAME = "Channel One"
        private const val NOTIFICATION_ID = 11
    }
}
