package woowacourse.movie.view.extension

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context

fun Context.alarmManager(): AlarmManager {
    return getSystemService(Context.ALARM_SERVICE) as AlarmManager
}

fun Context.notificationManager(): NotificationManager {
    return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}
