package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences

object AlarmSettingRepositoryImpl {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            "notifications_prefs",
            Context.MODE_PRIVATE
        )
    }

    var enablePushNotification: Boolean
        get() = prefs.getBoolean(NOTIFICATIONS, false)
        set(value) {
            prefs.edit().putBoolean(NOTIFICATIONS, value).apply()
        }

    private const val NOTIFICATIONS = "notifications"
}
