package woowacourse.movie.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class NotificationPreferenceManager(
    private val prefs: SharedPreferences,
) {
    constructor(context: Context) : this(
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    )

    fun isNotificationEnabled(): Boolean = prefs.getBoolean(KEY_NOTIFICATION_ENABLED, false)

    fun updateNotificationEnabled(isEnabled: Boolean) {
        prefs.edit { putBoolean(KEY_NOTIFICATION_ENABLED, isEnabled) }
    }

    companion object {
        private const val PREF_NAME = "SettingPrefs"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"
    }
}
