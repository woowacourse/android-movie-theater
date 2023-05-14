package woowacourse.movie.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.permission.PermissionManager

object NotificationManager {
    fun createChannel(context: Context, id: String, name: String, description: String) {
        val channel =
            NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                this.description = description
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun notify(
        context: Context,
        channelId: String,
        notificationId: Int,
        icon: Int,
        title: String,
        content: String,
        intent: PendingIntent
    ) {
        val notificationBuilder = createBuilder(context, channelId, icon, title, content, intent)

        if (PermissionManager.checkGrantedPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
        ) {
            NotificationManagerCompat
                .from(context)
                .notify(notificationId, notificationBuilder.build())
        }
    }

    private fun createBuilder(
        context: Context,
        channelId: String,
        icon: Int,
        title: String,
        content: String,
        intent: PendingIntent
    ): Builder {
        val notificationBuilder = Builder(context, channelId).apply {
            setSmallIcon(icon)
            setContentTitle(title)
            setContentText(content)
            priority = PRIORITY_DEFAULT
            setContentIntent(intent)
            setAutoCancel(true)
        }
        return notificationBuilder
    }
}
