package woowacourse.movie.presentation.permision

import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.SCHEDULE_EXACT_ALARM
import android.Manifest.permission.USE_EXACT_ALARM
import android.os.Build
import woowacourse.movie.R

object Permission {
    val notificationPermissions: Array<String> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                POST_NOTIFICATIONS,
                USE_EXACT_ALARM,
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(SCHEDULE_EXACT_ALARM)
        } else {
            arrayOf()
        }

    fun String?.toPermissionText(): Int {
        return when (this) {
            POST_NOTIFICATIONS -> R.string.permission_notification_text
            USE_EXACT_ALARM -> R.string.permission_alarm_text
            SCHEDULE_EXACT_ALARM -> R.string.permission_alarm_text
            else -> R.string.permission_default_text
        }
    }
}
