package woowacourse.movie.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log

object NotificationChannelCreator {
    fun registerNotificationChannel(
        notificationManager: NotificationManager,
        channelID: String,
        channelName: String,
        channelDescription: String
    ) {
        runCatching {
            notificationManager.getNotificationChannel(channelID)
                ?: throw IllegalArgumentException()
        }
            .getOrElse {
                notificationManager.createNotificationChannel(
                    createChannel(channelID, channelName, channelDescription)
                )
                Log.d("sunny", "created Channel")
            }
    }

    private fun createChannel(
        id: String,
        channelName: String,
        channelDescription: String
    ): NotificationChannel {
        val notificationChannel = NotificationChannel(
            id,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
        }

        return notificationChannel
    }
}
