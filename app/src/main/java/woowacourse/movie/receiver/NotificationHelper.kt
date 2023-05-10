package woowacourse.movie.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import woowacourse.movie.database.SettingPreferencesManager

object NotificationHelper {
    fun notifyNotification(
        notificationManager: NotificationManager,
        notification: Notification,
        notificationId: Int
    ) {
        if (SettingPreferencesManager.getData()) notificationManager.notify(
            notificationId,
            notification
        )
    }

    fun generateNotification(
        context: Context,
        channelId: String,
        notificationData: NotificationData,
    ): Notification {
        val notificationBuilder = notificationData.generateNotificationBuilder(context, channelId)
        return notificationBuilder.build()
    }

    fun generateNotificationManger(
        context: Context,
        channelId: String,
        channelName: String
    ): NotificationManager {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )
        return notificationManager
    }
}
