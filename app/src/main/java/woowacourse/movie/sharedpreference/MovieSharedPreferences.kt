package woowacourse.movie.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class MovieSharedPreferences(context: Context) {
    private val prefs = getSharedPreferences(context)

    fun getNotificationPreference(): Boolean {
        return prefs.getBoolean(KEY_NOTIFICATION, false)
    }

    fun setNotificationPreference(updatedPref: Boolean) {
        val editor = prefs.edit()
        with(editor) {
            putBoolean(KEY_NOTIFICATION, updatedPref)
            apply()
        }
    }

    companion object {
        fun getSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)

        private const val SHARED_PREFS_FILE_NAME = "movie"
        const val KEY_NOTIFICATION = "notifications"
    }
}
