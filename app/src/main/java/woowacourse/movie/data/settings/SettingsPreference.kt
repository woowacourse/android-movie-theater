package woowacourse.movie.data.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SettingsPreference private constructor(
    context: Context,
) : SettingsData {

    private val settingsPreferences: SharedPreferences

    override var isAvailable: Boolean
        get() = settingsPreferences.getBoolean(prefkey, true)
        set(value) {
            settingsPreferences.edit().putBoolean(prefkey, value).apply()
        }

    init {
        settingsPreferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    }

    companion object {
        private const val PREFERENCE_NAME = "Settings"

        private var instance: SettingsPreference? = null
        private var prefkey: String? = null

        fun getInstance(key: String, context: Context): SettingsPreference {
            return instance ?: synchronized(this) {
                instance ?: SettingsPreference(context).also {
                    instance = it
                    prefkey = key
                }
            }
        }
    }
}
