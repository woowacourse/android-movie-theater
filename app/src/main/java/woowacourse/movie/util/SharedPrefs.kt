package woowacourse.movie.util

import android.content.Context

class SharedPrefs(context: Context) {
    private val sharedPrefs =
        context.getSharedPreferences(
            ALARM_SETTING,
            Context.MODE_PRIVATE,
        )

    fun saveAlarmSetting(onSwitch: Boolean) {
        val editor = sharedPrefs?.edit()
        editor?.putBoolean(ALARM_SETTING, onSwitch)?.apply()
    }

    fun getSavedAlarmSetting(): Boolean {
        return sharedPrefs?.getBoolean(ALARM_SETTING, false) ?: false
    }

    companion object {
        private const val ALARM_SETTING = "alarm_setting"
    }
}
