package woowacourse.app.data.pushAlarm

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SettingSharedPreference(context: Context) : PushAlarmDataSource {
    private val loginPreferences: SharedPreferences =
        context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)

    private var receivingPushAlarm: Boolean
        get() = loginPreferences.getBoolean(PUSH_ALARM, false)
        set(value) = loginPreferences.edit { putBoolean(PUSH_ALARM, value).apply() }

    private fun clear() {
        loginPreferences.edit { clear().apply() }
    }

    override fun setPushAlarmOn() {
        receivingPushAlarm = true
    }

    override fun setPushAlarmOff() {
        receivingPushAlarm = false
    }

    override fun getPushAlarmState(): Boolean = receivingPushAlarm

    companion object {
        private const val SETTING = "SETTING"
        private const val PUSH_ALARM = "PUSH_ALARM"
    }
}
