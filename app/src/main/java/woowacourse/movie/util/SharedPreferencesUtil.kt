package woowacourse.movie.util

import android.content.Context

class SharedPreferencesUtil(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(
            ALARM_SETTING,
            Context.MODE_PRIVATE,
        )

    fun saveAlarmSetting(onSwitch: Boolean) {
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(ALARM_SETTING, onSwitch)?.apply()
    }

    fun getSavedAlarmSetting(): Boolean {
        return sharedPreferences?.getBoolean(ALARM_SETTING, false) ?: false
    }

    companion object {
        private const val ALARM_SETTING = "alarm_setting"
    }
}
