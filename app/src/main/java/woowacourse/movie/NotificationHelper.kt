package woowacourse.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationHelper {
    const val CHANNEL_ID = "reservation_channel_id"
    private const val CHANNEL_NAME = "Reservation Notifications"
    private const val CHANNEL_DESCRIPTION = "영화 예매 30분 전 알림을 위한 채널"

    fun createReservationChannel(context: Context) {
        val channelId = CHANNEL_ID
        val channelName = CHANNEL_NAME
        val channelDescription = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
