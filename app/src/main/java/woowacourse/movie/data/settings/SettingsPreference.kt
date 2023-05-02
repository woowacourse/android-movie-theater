package woowacourse.movie.data.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SettingsPreference private constructor(
    private val key: String,
    context: Context,
) : SettingsData {

    private val settingsPreferences: SharedPreferences

    override var isAvailable: Boolean
        get() = settingsPreferences.getBoolean(key, true)
        set(value) {
            settingsPreferences.edit().putBoolean(key, value).apply()
        }

    init {
        settingsPreferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    }

    companion object {
        private const val PREFERENCE_NAME = "Settings"

        private val instances = mutableMapOf<String, SettingsPreference>()

        fun getInstance(key: String, context: Context): SettingsPreference {
            return instances[key] ?: synchronized(this) {
                instances[key] ?: SettingsPreference(key, context).also {
                    instances[key] = it
                }
            }
        }
    }
}
