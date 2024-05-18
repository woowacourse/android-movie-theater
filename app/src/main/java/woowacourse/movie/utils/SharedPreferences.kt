package woowacourse.movie.utils

import android.content.Context

object SharedPreferences {
    private const val ALARM_SETTING = "alarm_setting"
    private const val KEY_ALARM_ENABLED = "alarmEnabled"

    fun saveAlarmSetting(
        context: Context,
        onSwitch: Boolean,
    ) {
        val prefs = context.getSharedPreferences(ALARM_SETTING, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_ALARM_ENABLED, onSwitch).apply()
    }

    fun getAlarmSetting(context: Context): Boolean {
        val prefs = context.getSharedPreferences(ALARM_SETTING, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_ALARM_ENABLED, false)
    }
}
