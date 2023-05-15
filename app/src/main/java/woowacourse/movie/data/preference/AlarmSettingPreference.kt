package woowacourse.movie.data.preference

import android.content.Context
import android.content.SharedPreferences

object AlarmSettingPreference {
    private lateinit var prefs: SharedPreferences
    private const val NOTIFICATIONS = "notifications"

    var enablePushNotification: Boolean
        get() = prefs.getBoolean(NOTIFICATIONS, false)
        set(value) {
            prefs.edit().putBoolean(NOTIFICATIONS, value).apply()
        }

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            "notifications_prefs",
            Context.MODE_PRIVATE
        )
    }
}
