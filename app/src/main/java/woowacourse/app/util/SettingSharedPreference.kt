package woowacourse.app.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SettingSharedPreference(context: Context) {
    private val loginPreferences: SharedPreferences =
        context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)

    var receivingPushAlarm: Boolean
        get() = loginPreferences.getBoolean(PUSH_ALARM, false)
        set(value) = loginPreferences.edit { putBoolean(PUSH_ALARM, value).apply() }

    fun clear() {
        loginPreferences.edit { clear().apply() }
    }

    companion object {
        private const val SETTING = "SETTING"
        private const val PUSH_ALARM = "PUSH_ALARM"
    }
}
