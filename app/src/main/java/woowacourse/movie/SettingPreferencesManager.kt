package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object SettingPreferencesManager {
    private const val KEY_PUSH_ALARM = "KEY_PUSH_ALARM"
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun changeAlarmReceptionStatus() {
        sharedPreferences.edit().putBoolean(KEY_PUSH_ALARM, !getAlarmReceptionStatus())
            .apply()
    }

    fun getAlarmReceptionStatus(): Boolean {
        return sharedPreferences.getBoolean(KEY_PUSH_ALARM, false)
    }
}
