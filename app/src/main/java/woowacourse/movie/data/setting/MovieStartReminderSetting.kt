package woowacourse.movie.data.setting

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class MovieStartReminderSetting private constructor(val context: Context) : AlarmSetting {

    override var isEnable: Boolean
        get() = prefs.getBoolean(MOVIE_REMINDER_NOTIFICATION_KEY, false)
        set(value) = prefs.edit().putBoolean(MOVIE_REMINDER_NOTIFICATION_KEY, value).apply()

    private val prefs: SharedPreferences = context.getSharedPreferences(SETTING_PREFERENCES_KEY, MODE_PRIVATE)

    companion object {
        private const val SETTING_PREFERENCES_KEY = "setting_preferences_key"
        private const val MOVIE_REMINDER_NOTIFICATION_KEY = "movie_remainder_notification_key"

        private var instance: AlarmSetting? = null

        fun getInstance(context: Context): AlarmSetting {
            return instance ?: synchronized(this) {
                instance ?: MovieStartReminderSetting(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }
}
