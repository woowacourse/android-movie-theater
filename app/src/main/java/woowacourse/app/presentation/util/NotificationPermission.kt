package woowacourse.app.presentation.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import woowacourse.app.presentation.ui.seat.NotiChannel

fun Context.requestNotificationPermission(launchAction: () -> Unit) {
    val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
    if (permission == PackageManager.PERMISSION_DENIED) {
        checkTiramisu(launchAction)
    }
}

fun checkTiramisu(action: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        action()
    }
}

fun createChannel(context: Context, channel: NotiChannel, importance: Int) {
    val mChannel = NotificationChannel(
        channel.channelName,
        channel.channelName,
        importance,
    ).apply {
        description = channel.channelDescription
    }
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)
}
