package woowacourse.movie.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.GlobalApplication

class NotificationPreferenceManager(
    private val prefs: SharedPreferences = GlobalApplication.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE),
) {
    var isNotificationEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_ENABLED, false)
        set(value) =
            prefs.edit { putBoolean(KEY_NOTIFICATION_ENABLED, value) }

    companion object {
        private const val PREF_NAME = "NotificationPrefs"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"
    }
}
