package woowacourse.movie.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AlarmSwitchState private constructor(context: Context) {
    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCE_NAME,
            Context.MODE_PRIVATE,
        )
    }
    var isAlarmActivated: Boolean
        set(value) = preferences.edit { putBoolean(KEY_SWITCH, value) }
        get() = preferences.getBoolean(KEY_SWITCH, false)

    companion object {
        private const val PREFERENCE_NAME = "movie"
        private const val KEY_SWITCH = "switch"
        private var instance: AlarmSwitchState? = null

        fun getInstance(context: Context): AlarmSwitchState =
            instance ?: synchronized(this) {
                instance ?: AlarmSwitchState(context).also { alarmSwitchState ->
                    instance = alarmSwitchState
                }
            }
    }
}
