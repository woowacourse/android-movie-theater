package woowacourse.movie.data

import android.content.Context
import android.content.Context.MODE_PRIVATE

class UserSettings private constructor() {

    private val prefsKey: String = "settings"

    fun set(context: Context, key: String, value: Boolean) {
        val prefs = context.getSharedPreferences(prefsKey, MODE_PRIVATE)
        prefs.edit().putBoolean(key, value).apply()
    }

    fun get(context: Context, key: String): Boolean {
        val prefs = context.getSharedPreferences(prefsKey, MODE_PRIVATE)
        return prefs.getBoolean(key, false)
    }

    companion object {
        const val NOTIFICATION_KEY = "notification_key"

        private var instance: UserSettings? = null

        fun getInstance(): UserSettings {
            return instance ?: synchronized(this) {
                instance ?: UserSettings().also {
                    instance = it
                }
            }
        }
    }
}
