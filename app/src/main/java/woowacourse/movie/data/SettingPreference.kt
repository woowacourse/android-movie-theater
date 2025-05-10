package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SettingPreference(context: Context) {
    private val preference: SharedPreferences =
        context.getSharedPreferences(
            SHARED_NAME,
            Context.MODE_PRIVATE,
        )

    fun isAlarmPermitted(): Boolean = preference.getBoolean(SHARED_SET_ALARM, false)

    fun setAlarmPermitted(isGranted: Boolean) {
        preference.edit { putBoolean(SHARED_SET_ALARM, isGranted) }
    }

    companion object {
        private const val SHARED_NAME = "settings"
        private const val SHARED_SET_ALARM = "notification"
    }
}
