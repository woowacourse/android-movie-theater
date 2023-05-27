package woowacourse.movie.data.setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class MovieReminderSetting private constructor() : AlarmSetting {

    // 이렇게 할 경우 GC가 무한 호출되어 앱이 종료되는 오류가 발생하는 이유를 모르겠습니다...
//    private val context: Context by lazy { context.applicationContext }
//    private val prefs: SharedPreferences by lazy { context.getSharedPreferences(SETTING_PREFERENCES_KEY, MODE_PRIVATE) }

    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences

    override var isEnable: Boolean
        get() = prefs.getBoolean(MOVIE_REMINDER_NOTIFICATION_KEY, false)
        set(value) = prefs.edit().putBoolean(MOVIE_REMINDER_NOTIFICATION_KEY, value).apply()

    fun init(context: Context): MovieReminderSetting {
        this.context = context.applicationContext
        prefs = context.getSharedPreferences(SETTING_PREFERENCES_KEY, MODE_PRIVATE)
        return instance!!
    }

    fun releaseInstance() {
        instance = null
    }

    companion object {
        private const val SETTING_PREFERENCES_KEY = "setting_preferences_key"
        private const val MOVIE_REMINDER_NOTIFICATION_KEY = "movie_remainder_notification_key"

        @SuppressLint("StaticFieldLeak")
        private var instance: MovieReminderSetting? = null

        fun getInstance(): MovieReminderSetting {
            return instance ?: synchronized(this) {
                instance ?: MovieReminderSetting().also {
                    instance = it
                }
            }
        }
    }
}
