package woowacourse.movie.data.preferences

import android.content.Context
import android.content.SharedPreferences

class MoviePreferencesUtil(private val context: Context) {
    fun getBoolean(key: String): Boolean {
        return getSharedPreferences(context).getBoolean(key, false)
    }

    fun setBoolean(
        key: String,
        newValue: Boolean,
    ): Boolean {
        getSharedPreferences(context).edit().putBoolean(key, newValue).apply()
        return newValue
    }

    companion object {
        fun getSharedPreferences(context: Context): SharedPreferences {
            return context.applicationContext.getSharedPreferences(
                "prefs_name",
                Context.MODE_PRIVATE,
            )
        }
    }
}
