package woowacourse.movie.model.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AlarmSwitchStateImpl private constructor(
    context: Context,
) : AlarmSwitchState {
    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCE_NAME,
            Context.MODE_PRIVATE,
        )
    }
    override var isAlarmActivated: Boolean
        set(value) = preferences.edit { putBoolean(KEY_SWITCH, value) }
        get() = preferences.getBoolean(KEY_SWITCH, false)

    companion object {
        private const val PREFERENCE_NAME = "movie"
        private const val KEY_SWITCH = "switch"
        private var instance: AlarmSwitchStateImpl? = null

        fun getInstance(context: Context): AlarmSwitchStateImpl =
            instance ?: synchronized(this) {
                instance ?: AlarmSwitchStateImpl(context).also { alarmSwitchState ->
                    instance = alarmSwitchState
                }
            }
    }
}
