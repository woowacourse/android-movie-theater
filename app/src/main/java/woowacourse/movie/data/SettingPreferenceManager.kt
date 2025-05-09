package woowacourse.movie.data

import android.content.Context
import androidx.core.content.edit

class SettingPreferenceManager(
    context: Context,
) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getPushAlarmEnabled(): Boolean = prefs.getBoolean("notification", false)

    fun setPushAlarmEnabled(enabled: Boolean) {
        prefs.edit {
            putBoolean("notification", enabled)
        }
    }
}
