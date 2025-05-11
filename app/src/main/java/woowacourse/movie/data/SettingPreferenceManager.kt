package woowacourse.movie.data

import android.content.Context
import androidx.core.content.edit

class SettingPreferenceManager(
    context: Context,
) {
    private val prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getNotificationEnabled(): Boolean = prefs.getBoolean(NOTIFICATION_KEY, false)

    fun setNotificationEnabled(enabled: Boolean) {
        prefs.edit {
            putBoolean(NOTIFICATION_KEY, enabled)
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "notification_pref"
        private const val NOTIFICATION_KEY = "notification"
    }
}
