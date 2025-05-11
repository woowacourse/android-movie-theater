package woowacourse.movie.data.notification

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.MovieApplication

class NotificationPreferenceImpl : NotificationPreference {
    private val prefs: SharedPreferences =
        MovieApplication.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun isNotificationEnabled(): Boolean {
        return prefs.getBoolean(KEY_NOTIFICATION_ENABLED, true)
    }

    override fun setNotificationEnabled(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_NOTIFICATION_ENABLED, enabled) }
    }

    companion object {
        private const val PREF_NAME = "notification_setting"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"
    }
}