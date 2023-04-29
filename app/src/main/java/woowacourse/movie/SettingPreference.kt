package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

class SettingPreference(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(setting_preference_key, Context.MODE_PRIVATE)

    var setting: Boolean
        get() = sharedPreferences.getBoolean(setting_value_key, false)
        set(value) = sharedPreferences.edit().putBoolean(setting_value_key, value).apply()

    companion object {
        private const val setting_preference_key = "setting"
        private const val setting_value_key = "setting_value"
    }
}
