package woowacourse.movie.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import java.util.concurrent.atomic.AtomicInteger

private const val CHANNEL_ID = "movie_channel"
private const val CHANNEL_NAME = "movie_channel_name"
private const val CHANNEL_DESCRIPTION = "This is a movie channel"

private val atomicNotifyId = AtomicInteger(0x001)

const val NOTIFICATION_FAIL_NO_PERMISSION = -1

data class NotificationSettings(
    @DrawableRes val iconResId: Int = R.drawable.ic_launcher_foreground,
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

fun Context.createNotification(notificationSettings: NotificationSettings): Notification {
    createNotificationChannel()

    return NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(notificationSettings.iconResId)
        .setContentTitle(notificationSettings.contentTitle)
        .setContentText(notificationSettings.contentText)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setAutoCancel(notificationSettings.cancelable)
        .build()
}

/**
 * return values
 * NOTIFICATION_FAIL_NO_PERMISSION = -1
 */
fun Context.sendNotification(notificationSettings: NotificationSettings): Int {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        return NOTIFICATION_FAIL_NO_PERMISSION
    }

    val notification = this.createNotification(notificationSettings)
    val notificationId = atomicNotifyId.getAndIncrement()
    NotificationManagerCompat.from(this).notify(notificationId, notification)
    return notificationId
}
