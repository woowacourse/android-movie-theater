package woowacourse.movie.data.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SettingsPreference : SettingsData {

    private lateinit var settingsPreferences: SharedPreferences
    private const val preferenceName = "Settings"

    fun init(context: Context) {
        settingsPreferences = context.getSharedPreferences(preferenceName, MODE_PRIVATE)
    }

    override fun setBooleanData(key: String, value: Boolean) {
        settingsPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBooleanData(key: String, defaultValue: Boolean): Boolean =
        settingsPreferences.getBoolean(key, defaultValue)
}
