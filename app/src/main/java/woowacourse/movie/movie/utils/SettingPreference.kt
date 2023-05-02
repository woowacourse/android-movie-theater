package woowacourse.movie.movie.utils

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.movie.MainActivity.Companion.setting_preference_key

object SettingPreference {

    private lateinit var sharedPreferences: SharedPreferences

    fun setBoolean(context: Context, key: String, value: Boolean) {
        sharedPreferences = context.getSharedPreferences(setting_preference_key, Context.MODE_PRIVATE)
        val editer = sharedPreferences.edit()
        editer.putBoolean(key, value)
        editer.apply()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        sharedPreferences = context.getSharedPreferences(setting_preference_key, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }
}
