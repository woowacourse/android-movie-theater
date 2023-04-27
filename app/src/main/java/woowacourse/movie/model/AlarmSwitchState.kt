package woowacourse.movie.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object AlarmSwitchState {
    private const val PREFERENCE_NAME = "movie"
    private const val KEY_SWITCH = "switch"

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    var isAlarmActivated: Boolean
        set(value) = preferences.edit { putBoolean(KEY_SWITCH, value) }
        get() = preferences.getBoolean(KEY_SWITCH, false)
}
