package woowacourse.movie.helper

import android.content.Context
import androidx.core.content.edit

object SettingsPreferenceHelper {
    private const val PREF_NAME = "settings"
    private const val KEY_NOTIFICATION = "notification"

    fun isNotificationEnabled(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_NOTIFICATION, true)
    }

    fun setNotificationEnabled(context: Context, enabled: Boolean) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit { putBoolean(KEY_NOTIFICATION, enabled) }
    }
}
