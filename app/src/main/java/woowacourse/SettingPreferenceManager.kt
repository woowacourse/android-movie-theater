package woowacourse

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_PUSH_ALARM = "KEY_PUSH_ALARM"

object SettingPreferenceManager {
    private lateinit var sharedPreference: SharedPreferences
    fun inIt(context: Context) {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun changeAlarmReceptionStatus() {
        sharedPreference.edit().putBoolean(KEY_PUSH_ALARM, !getAlarmReceptionStatus())
            .apply()
    }

    fun getAlarmReceptionStatus(): Boolean {
        return sharedPreference.getBoolean(KEY_PUSH_ALARM, false)
    }
}
