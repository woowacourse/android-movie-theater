package woowacourse.movie.sharedpreference

import android.content.Context

class SharedPreferences(context: Context) {
    private val prefs = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)

    fun getNotificationPreference(): Boolean {
        return prefs.getBoolean(KEY_NOTIFICATION, false)
    }

    fun setNotificationPreference(updatedPref: Boolean) {
        prefs.edit().apply {
            putBoolean(KEY_NOTIFICATION, updatedPref)
            apply()
        }
    }

    companion object {
        const val SHARED_PREFS_FILE_NAME = "movie"
        const val KEY_NOTIFICATION = "notification"
    }
}
