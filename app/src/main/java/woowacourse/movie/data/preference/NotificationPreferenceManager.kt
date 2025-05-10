package woowacourse.movie.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class NotificationPreferenceManager(
    private val prefs: SharedPreferences,
) {
    var isNotificationEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_ENABLED, false)
        set(value) =
            prefs.edit { putBoolean(KEY_NOTIFICATION_ENABLED, value) }

    companion object {
        private const val PREF_NAME = "NotificationPrefs"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"

        private var instance: NotificationPreferenceManager? = null

        fun getInstance(context: Context): NotificationPreferenceManager {
            if (instance == null) {
                val prefs = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                instance = NotificationPreferenceManager(prefs)
            }
            return instance!!
        }
    }
}
