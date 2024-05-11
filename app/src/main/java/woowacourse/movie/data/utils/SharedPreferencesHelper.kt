package woowacourse.movie.data.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {
    private const val PREF_NAME = "movie_app_prefs"
    private const val NOTIFICATION_KEY = "notification_key"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setNotificationEnabled(context: Context, isEnabled: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(NOTIFICATION_KEY, isEnabled)
        editor.apply()
    }

    fun isNotificationEnabled(context: Context): Boolean {
        return getPreferences(context).getBoolean(NOTIFICATION_KEY, false)
    }
}
