package woowacourse.movie.movie.alarm

import android.content.Context
import android.content.SharedPreferences

class SettingPreference(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(setting_preference_key, Context.MODE_PRIVATE)

    fun setBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    companion object {
        const val setting_preference_key = "setting"
    }
}
