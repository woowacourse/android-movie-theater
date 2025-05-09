package woowacourse.movie.data

import android.content.Context
import androidx.core.content.edit

class SettingPreferenceManager(
    context: Context,
) {
    private val prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getPushAlarmEnabled(): Boolean = prefs.getBoolean(KEY_PUSH_ALARM, false)

    fun setPushAlarmEnabled(enabled: Boolean) {
        prefs.edit {
            putBoolean(KEY_PUSH_ALARM, enabled)
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "settings"
        private const val KEY_PUSH_ALARM = "notification"
    }
}
