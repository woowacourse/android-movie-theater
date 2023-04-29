package woowacourse.movie.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SettingsData {

    private lateinit var settingsPreferences: SharedPreferences
    private const val preferenceName = "Settings"

    fun init(context: Context) {
        settingsPreferences = context.getSharedPreferences(preferenceName, MODE_PRIVATE)
    }

    fun setBooleanData(key: String, value: Boolean) {
        settingsPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanData(key: String): Boolean = settingsPreferences.getBoolean(key, true)
}
