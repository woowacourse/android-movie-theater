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

private val atomicNotifyId = AtomicInteger(0x001)

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

fun Context.createNotification(
    iconResId: Int,
    contentTitle: String,
    contentText: String,
    cancelable: Boolean,
): Notification {
    createNotificationChannel()

    return NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(iconResId)
        .setContentTitle(contentTitle)
        .setContentText(contentText)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setAutoCancel(cancelable)
        .build()
}

fun Context.sendNotification(
    iconResId: Int,
    contentTitle: String,
    contentText: String,
    cancelable: Boolean,
): Int {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        Log.d("NotificationExt", "sendNotification: no permission")
        return -1
    }

    val notification = this.createNotification(iconResId, contentText, contentTitle, cancelable)
    val notificationId = atomicNotifyId.getAndIncrement()
    NotificationManagerCompat.from(this).notify(notificationId, notification)
    return notificationId
}
