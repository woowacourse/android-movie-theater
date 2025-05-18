package woowacourse.movie.sharedPreference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

class SettingSharedPreferenceManager(
    context: Context,
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE)

    fun updateNotificationEnabled(isEnabled: Boolean) {
        prefs.edit {
            putBoolean("notification", isEnabled)
        }
    }

    fun isNotificationEnabled(): Boolean = prefs.getBoolean("notification", false)
}
