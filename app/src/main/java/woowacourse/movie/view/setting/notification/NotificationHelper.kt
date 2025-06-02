package woowacourse.movie.view.setting.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import woowacourse.movie.R

object NotificationHelper {
    const val CHANNEL_ID = "alarm_channel"

    fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.reservation_notification_channel_name)
        val descriptionText =
            context.getString(R.string.reservation_notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel =
            NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
