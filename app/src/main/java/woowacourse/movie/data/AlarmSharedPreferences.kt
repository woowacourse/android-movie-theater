package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences

class AlarmSharedPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(ALARM, Context.MODE_PRIVATE)

    fun setAlarm(input: Boolean) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean(ALARM_SETTING, input)
        editor.commit()
    }

    fun isReservationAlarm(): Boolean = prefs.getBoolean(ALARM_SETTING, false)

    companion object {
        private const val ALARM: String = "alarm"
        private const val ALARM_SETTING: String = "alarmSetting"
    }
}
