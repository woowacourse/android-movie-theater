package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

class AlarmPreference(context: Context) {
    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(ALARM_SETTING, Context.MODE_PRIVATE)

    fun getBoolean(key: String, defValue: Boolean): Boolean =
        sharedPreference.getBoolean(key, defValue)

    fun putBoolean(key: String, value: Boolean) {
        sharedPreference.edit().putBoolean(key, value).apply()
    }

    companion object {
        const val ALARM_SETTING = "ALARM_SETTING"
    }
}
