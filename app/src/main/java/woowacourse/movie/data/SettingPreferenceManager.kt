package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.MovieApplication

class SettingPreferenceManager(
    private val prefs: SharedPreferences =
        MovieApplication.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE),
) {
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
