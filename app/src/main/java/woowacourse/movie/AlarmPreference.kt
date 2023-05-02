package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

class AlarmPreference private constructor(context: Context) {
    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(ALARM_SETTING, Context.MODE_PRIVATE)

    fun isAlarmOn(defValue: Boolean): Boolean =
        sharedPreference.getBoolean(IS_ALARM_ON, defValue)

    fun setIsAlarmOn(value: Boolean) {
        sharedPreference.edit().putBoolean(IS_ALARM_ON, value).apply()
    }

    companion object {
        private const val ALARM_SETTING = "ALARM_SETTING"
        private const val IS_ALARM_ON = "IS_ALARM_ON"

        private val alarmPreference: AlarmPreference? = null

        fun getInstance(context: Context): AlarmPreference {
            return alarmPreference ?: synchronized(this) {
                AlarmPreference(context)
            }
        }
    }
}
