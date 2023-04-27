package woowacourse.movie.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.atomic.AtomicInteger

private const val CHANNEL_ID = "movie_channel"
private const val CHANNEL_NAME = "movie_channel_name"
private const val CHANNEL_DESCRIPTION = "This is a movie channel"
private const val NO_PERMISSION_OF_NOTIFICATION = -1

private val atomicNotifyId = AtomicInteger(0x001)

data class NotificationArgs(
    val iconResId: Int,
    val contentTitle: String,
    val contentText: String,
    val cancelable: Boolean
)

fun Context.createNotificationChannel() {
    val id = CHANNEL_ID
    val name = CHANNEL_NAME
    val descriptionText = CHANNEL_DESCRIPTION
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(id, name, importance).apply {
        description = descriptionText
        setShowBadge(true)
        setBypassDnd(true)
        lightColor = Color.RED
        enableLights(true)
        lockscreenVisibility = Notification.VISIBILITY_SECRET
    }
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

fun Context.createNotification(args: NotificationArgs): Notification {
    createNotificationChannel()

    return NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(args.iconResId)
        .setContentTitle(args.contentTitle)
        .setContentText(args.contentText)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setAutoCancel(args.cancelable)
        .build()
}

fun Context.sendNotification(args: NotificationArgs): Int {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        Log.d("NotificationExt", "sendNotification: no permission")
        return NO_PERMISSION_OF_NOTIFICATION
    }

    val notification = this.createNotification(args)
    val notificationId = atomicNotifyId.getAndIncrement()
    NotificationManagerCompat.from(this).notify(notificationId, notification)
    return notificationId
}
