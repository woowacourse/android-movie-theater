package woowacourse.movie.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager private constructor(
    context: Context,
) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var isNotificationEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_ENABLED, false)
        set(value) =
            prefs.edit { putBoolean(KEY_NOTIFICATION_ENABLED, value) }

    companion object {
        private const val PREF_NAME = "NotificationPrefs"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"

        private var instance: PreferenceManager? = null

        fun getInstance(context: Context): PreferenceManager {
            if (instance == null) {
                instance = PreferenceManager(context)
            }
            return instance!!
        }
    }
}
