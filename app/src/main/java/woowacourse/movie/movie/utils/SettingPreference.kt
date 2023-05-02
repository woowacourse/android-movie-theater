package woowacourse.movie.movie.utils

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.movie.MainActivity.Companion.setting_preference_key

object SettingPreference {

    private lateinit var sharedPreferences: SharedPreferences
    // private val sharedPreferences: SharedPreferences =
    //     context.getSharedPreferences(setting_preference_key, Context.MODE_PRIVATE)

    fun setBoolean(context: Context, key: String, value: Boolean){
        sharedPreferences = context.getSharedPreferences(setting_preference_key, Context.MODE_PRIVATE)
        val editer = sharedPreferences.edit()
        editer.putBoolean(key, value)
        editer.apply()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        sharedPreferences = context.getSharedPreferences(setting_preference_key, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    // fun setBoolean(key: String, value: Boolean) {
    //     val editor = sharedPreferences.edit()
    //     editor.putBoolean(key, value)
    //     editor.apply()
    // }
    //
    // fun getBoolean(key: String): Boolean {
    //     return sharedPreferences.getBoolean(key, false)
    // }
}
