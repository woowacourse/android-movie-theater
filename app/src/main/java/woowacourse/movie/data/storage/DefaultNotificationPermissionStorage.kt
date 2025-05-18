package woowacourse.movie.data.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class DefaultNotificationPermissionStorage(
    context: Context,
) : NotificationPermissionStorage {
    private val sharedPreference =
        context.applicationContext.getSharedPreferences(
            NOTIFICATION_PERMISSION_DATA_KEY,
            MODE_PRIVATE,
        )
    private val editor: SharedPreferences.Editor = sharedPreference.edit()

    override val notificationPermission: Boolean
        get() = sharedPreference.getBoolean(NOTIFICATION_KEY, false)

    override fun updateNotificationPermission(isGranted: Boolean) {
        editor.putBoolean(NOTIFICATION_KEY, isGranted).commit()
    }

    companion object {
        private const val NOTIFICATION_PERMISSION_DATA_KEY = "notification_permission"
        private const val NOTIFICATION_KEY = "notification"
    }
}
