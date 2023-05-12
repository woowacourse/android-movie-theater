package woowacourse.movie.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.permission.SinglePermissionRequester

class NotificationCreator(private val context: Context, private val channelID: String) {
    private val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelID)

    fun registerNotificationChannel(
        channelName: String,
        channelDescription: String
    ) {
        val manager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        NotificationChannelCreator.registerNotificationChannel(
            manager,
            channelID,
            channelName,
            channelDescription
        )
    }

    fun setNotificationView(
        iconDrawable: Int,
        contentTitle: String,
        contentText: String,
        pendingIntent: PendingIntent?
    ) {
        with(builder) {
            setSmallIcon(iconDrawable)
            setContentTitle(contentTitle)
            setContentText(contentText)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
    }

    fun notify(notificationChannelID: Int) {
        with(NotificationManagerCompat.from(context)) {
            if (!SinglePermissionRequester.checkDeniedPermission(
                    context,
                    SinglePermissionRequester.NOTIFICATION_PERMISSION
                )
            )
                notify(notificationChannelID, builder.build())
        }
    }
}
