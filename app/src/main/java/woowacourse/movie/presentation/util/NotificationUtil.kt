package woowacourse.movie.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationUtil {
    private const val CHANNEL_ID = "movie_alarm"
    private const val CHANNEL_NAME = "예매 알림"
    private const val CHANNEL_DESCRIPTION = "상영 30분 전 알림"

    private var isChannelCreated = false

    fun ensureNotificationChannel(context: Context) {
        if (isChannelCreated) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if (existingChannel == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = CHANNEL_DESCRIPTION
                }
                notificationManager.createNotificationChannel(channel)
            }
        }

        isChannelCreated = true
    }
}