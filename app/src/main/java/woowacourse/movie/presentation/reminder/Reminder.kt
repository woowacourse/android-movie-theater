package woowacourse.movie.presentation.reminder

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.extensions.checkPermissionCompat

abstract class Reminder {
    protected abstract val channelId: String
    protected abstract val channelName: String
    protected abstract val channelDesc: String

    abstract fun <T : Parcelable> notify(context: Context, data: T)

    @SuppressLint("MissingPermission")
    protected fun sendNotification(
        context: Context,
        title: String,
        content: String,
        pushId: Int,
        onClickNotification: () -> PendingIntent,
    ) {
        createChannel(context)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setShowWhen(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(onClickNotification())

        if (isPermissionGranted(context)) {
            NotificationManagerCompat.from(context).notify(pushId, builder.build())
        }
    }

    private fun createChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDesc
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("InlinedApi")
    private fun isPermissionGranted(context: Context): Boolean =
        context.checkPermissionCompat(permission.POST_NOTIFICATIONS)
}
