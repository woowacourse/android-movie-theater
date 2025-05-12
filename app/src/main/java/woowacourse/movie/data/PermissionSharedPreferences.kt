package woowacourse.movie.data

import android.content.Context
import androidx.core.content.edit

class PermissionSharedPreferences(private val context: Context) {
    private val preferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveNotificationPermissionResult(isAllowed: Boolean) {
        preferences.edit {
            putBoolean(KEY_NOTIFICATION_PERMISSION_STATUS, isAllowed)
            apply()
        }
    }

    fun notificationPermission(): Boolean {
        return preferences.getBoolean(KEY_NOTIFICATION_PERMISSION_STATUS, false)
    }

    companion object {
        private const val PREFS_NAME = "AppPreferences"
        private const val KEY_NOTIFICATION_PERMISSION_STATUS = "notification_permission"
    }
}
